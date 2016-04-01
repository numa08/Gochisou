package net.numa08.gochisou.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.presentation.view.fragment.NavigationAddable
import net.numa08.gochisou.presentation.view.fragment.PostDetailFragment
import org.jetbrains.anko.support.v4.withArguments
import org.parceler.Parcels
import javax.inject.Inject

class PostDetailActivity() : AppCompatActivity() {

    val fullName by lazy { intent!!.getStringExtra("fullName") }
    val loginProfile by lazy { Parcels.unwrap<LoginProfile>(intent!!.getParcelableExtra("loginProfile")) }

    lateinit var realmConfiguration: RealmConfiguration
        @Inject set
    lateinit var navigationIdentifierReposiotory: NavigationIdentifierRepository
        @Inject set

    lateinit var realm: Realm
    lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        realm = Realm.getInstance(realmConfiguration)
        post = realm.where(Post::class.java).equalTo("fullName", fullName).findFirst()
        setContentView(R.layout.activity_post_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.title = post.name
        supportActionBar?.subtitle = post.category
        val fragment = Fragment().withArguments("fullName" to fullName, "loginProfile" to Parcels.wrap(loginProfile))
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}

internal class Fragment : PostDetailFragment(), NavigationAddable {
    override var navigationIdentifierRepository: NavigationIdentifierRepository
            = GochisouApplication.application?.applicationComponent?.navigationIdentifierRepository()!!

    override val navigationIdentifier: NavigationIdentifier
            by lazy {
                NavigationIdentifier.PostDetailNavigationIdentifier(
                        name = post.name!!,
                        avatar = post.createdBy?.icon!!,
                        loginProfile = loginProfile,
                        fullName = post.fullName ?: "")
            }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if (!navigationIdentifierRepository.contains(navigationIdentifier)) {
            inflater?.inflate(R.menu.fragment_post_detail, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.add_navigation_identifier) {
            onAddClicked()
            activity?.supportFinishAfterTransition()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}