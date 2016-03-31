package net.numa08.gochisou.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_header.view.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.presentation.presenter.PostListPresenter
import net.numa08.gochisou.presentation.view.PostListView
import net.numa08.gochisou.presentation.view.fragment.MainNavigationFragment
import net.numa08.gochisou.presentation.view.fragment.PostListFragment
import org.jetbrains.anko.intentFor
import org.parceler.Parcels
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
        MainNavigationFragment.TabLayoutActivity,
        PostListFragment.PresenterProvider,
        PostListView,
        NavigationView.OnNavigationItemSelectedListener {

    companion object {

        val BACK_STACK = "${MainActivity::class.simpleName}.BACK_STACK"

    }

    lateinit var loginProfileRepository: LoginProfileRepository
        @Inject set

    lateinit var realmConfiguration: RealmConfiguration
        @Inject set
    lateinit override var postListPresenter: PostListPresenter
        @Inject set

    override val tabLayout: TabLayout
        get() = tabbar

    val realm by lazy { Realm.getInstance(realmConfiguration) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        postListPresenter = GochisouApplication.application?.applicationComponent?.postListPresenter()!!
        postListPresenter.postListView = this
        setContentView(R.layout.activity_main)
        navigation_view.setNavigationItemSelectedListener(this)
        if(loginProfileRepository.isEmpty()) {
            startActivity(LoginActivity.intent(this))
            supportFinishAfterTransition()
        } else {
            setSupportActionBar(toolbar)
            val drawerToggle = ActionBarDrawerToggle(this, drawer, toolbar, android.R.string.ok, android.R.string.no)
            drawer.addDrawerListener(drawerToggle)
            drawerToggle.syncState()

            loginProfileRepository.first().let {
                val team : Team? = realm.where(Team::class.java)
                    .equalTo("loginToken", it.token)
                    .findFirst()
                team?.let {
                    Picasso.with(this).load(it.icon).into(navigation_view.getHeaderView(0).image_navigation_header)
                    navigation_view.getHeaderView(0).text_navigation_header_title.text = it.name
                }
            }

            supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, MainNavigationFragment(), BACK_STACK)
            .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount == 1) {
            supportFinishAfterTransition()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem?): Boolean = item?.let {
        when (it.itemId) {
            R.id.edit_navigation_identifier -> {
                startActivity(intentFor<EditNavigationIdentifierActivity>())
                true
            }
            else -> {
                false
            }
        }
    } ?: false

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun showPost(fragment: PostListFragment, post: Post) {
        startActivity(intentFor<PostDetailActivity>(
                "fullName" to (post.fullName ?: ""),
                "loginProfile" to Parcels.wrap(fragment.loginProfile)))
    }

}