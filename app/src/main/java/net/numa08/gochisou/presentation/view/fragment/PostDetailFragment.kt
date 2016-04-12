package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.fragment_post_detail.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import javax.inject.Inject

class PostDetailFragment() : Fragment(), ArgLoginProfile, NavigationAddable {

    lateinit var realmConfiguration: RealmConfiguration
        @Inject set
    lateinit override var navigationIdentifierRepository: NavigationIdentifierRepository
        @Inject set

    lateinit var realm: Realm
    lateinit var post: Post

    override val navigationIdentifier: NavigationIdentifier
            by lazy {
                NavigationIdentifier.PostDetailNavigationIdentifier(
                        name = post.name!!,
                        avatar = post.createdBy?.icon!!,
                        loginProfile = loginProfile(),
                        fullName = post.fullName ?: "")
            }


    val fullName by lazy { arguments!!.getString("fullName") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        realm = Realm.getInstance(realmConfiguration)
        post = realm.where(Post::class.java).equalTo("fullName", fullName).findFirst()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_post_detail, container, false)
        setHasOptionsMenu(shouldShowAddButton())
        return v
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.loadData(post.bodyHTML, "text/html; charset=utf-8", "UTF-8")
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_post_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (super<NavigationAddable>.onOptionsItemSelected(item)) {
            activity?.supportFinishAfterTransition()
            return true
        }
        return super<Fragment>.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}

