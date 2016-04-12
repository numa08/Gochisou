package net.numa08.gochisou.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_post_list.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.presentation.view.PostListView
import net.numa08.gochisou.presentation.view.fragment.ArgLoginProfile
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
        val fragment = PostListFragment().withArguments(ArgLoginProfile.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile()))
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    override fun showPost(fragment: PostListFragment, post: Post) {
        showPostDetail(fragment, post)
    }

}
