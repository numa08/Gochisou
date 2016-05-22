package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_post_list.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.presentation.presenter.PostListPresenter
import net.numa08.gochisou.presentation.service.EsaAccessService
import net.numa08.gochisou.presentation.view.adapter.PostListAdapter
import net.numa08.gochisou.presentation.widget.DividerItemDecoration
import javax.inject.Inject

class PostListFragment : Fragment(), ArgLoginProfile, NavigationAddable {

    lateinit var realmConfiguration: RealmConfiguration
        @Inject set
    lateinit override var navigationIdentifierRepository: NavigationIdentifierRepository
        @Inject set


    lateinit var listAdapter : PostListAdapter
    val realm by lazy { Realm.getInstance(realmConfiguration) }
    val team by lazy { loginProfile().team }

    override val navigationIdentifier: NavigationIdentifier
            by lazy {
                NavigationIdentifier.PostNavigationIdentifier(team.name, team.icon, loginProfile())
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_post_list, container, false)
        setHasOptionsMenu(shouldShowAddButton())
        return v
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = object : PostListAdapter(loadPosts(), Picasso.with(view?.context)) {
            override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
                super.onBindViewHolder(holder, position)
                holder?.centerContent?.setOnClickListener { onClickItem(it, realmResults?.get(position)!!) }
            }
        }
        listAdapter = adapter
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(recycler.context, DividerItemDecoration.VERTICAL_LIST))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EsaAccessService.getPosts(context, loginProfile()).let { context.startService(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_post_list, menu)
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

    fun onClickItem(view: View, post: Post) {
        (activity as? PresenterProvider)?.postListPresenter?.onClickPost(this, post)
                ?: Log.d("Gochisou", "on click post at $view 's ${post.fullName}")
    }

    fun loadPosts(): RealmResults<Post>? =
            realm
            .where(Post::class.java)
            ?.equalTo("teamName", loginProfile()
                    .team
                    .name)
            ?.findAll()

    interface PresenterProvider {
        val postListPresenter: PostListPresenter
    }
}