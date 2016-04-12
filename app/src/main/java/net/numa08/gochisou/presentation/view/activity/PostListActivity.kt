package net.numa08.gochisou.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import kotlinx.android.synthetic.main.activity_post_list.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.presentation.view.PostListView
import net.numa08.gochisou.presentation.view.fragment.ArgLoginProfile
import net.numa08.gochisou.presentation.view.fragment.NavigationAddable
import net.numa08.gochisou.presentation.view.fragment.PostListFragment
import org.jetbrains.anko.support.v4.withArguments
import org.parceler.Parcels

class PostListActivity : AppCompatActivity(),
        IntentLoginProfile,
        PostListFragment.PresenterProvider,
        PostListView {

    override val postListPresenter by lazy { GochisouApplication.application?.applicationComponent?.activityComponent()?.postListPresenter()!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        postListPresenter.postListView = this
        setContentView(R.layout.activity_post_list)
        setSupportActionBar(toolbar)
        val fragment = Fragment().withArguments(ArgLoginProfile.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile()))
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    override fun showPost(fragment: PostListFragment, post: Post) {
        showPostDetail(fragment, post)
    }

    private class Fragment : PostListFragment(), NavigationAddable {
        override var navigationIdentifierRepository: NavigationIdentifierRepository
                = GochisouApplication.application?.applicationComponent?.navigationIdentifierRepository()!!

        override val navigationIdentifier: NavigationIdentifier
                by lazy {
                    NavigationIdentifier.PostNavigationIdentifier(team.name!!, team.icon!!, loginProfile())
                }

        val team: Team by lazy { realm.where(Team::class.java).equalTo("loginToken", loginProfile().token).findFirst() }

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            setHasOptionsMenu(true)
            return super.onCreateView(inflater, container, savedInstanceState)
        }

        override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
            super.onCreateOptionsMenu(menu, inflater)
            if (shouldShowAddButton()) {
                inflater?.inflate(R.menu.fragment_post_list, menu)
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

}
