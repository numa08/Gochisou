package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_post_list.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.presentation.presenter.PostListPresenter
import net.numa08.gochisou.presentation.service.EsaAccessService
import net.numa08.gochisou.presentation.view.adapter.PostListAdapter
import net.numa08.gochisou.presentation.widget.DividerItemDecoration
import org.parceler.Parcels
import javax.inject.Inject

open class PostListFragment : Fragment() {

    companion object {
        val ARG_LOGIN_PROFILE = "${PostListFragment::class.simpleName}.ARG_LOGIN_PROFILE"
    }

    lateinit var realmConfiguration: RealmConfiguration
        @Inject set


    val loginProfile by lazy { Parcels.unwrap<LoginProfile>(arguments!!.getParcelable(ARG_LOGIN_PROFILE)) }
    lateinit var listAdapter : PostListAdapter
    val realm by lazy { Realm.getInstance(realmConfiguration) }
    val realmHandler by lazy {{
        listAdapter.updateRealmResults(loadPosts())
    }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater?.inflate(R.layout.fragment_post_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val posts = loadPosts()
        val adapter = object : PostListAdapter(loadPosts(), Picasso.with(view?.context)) {
            override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
                super.onBindViewHolder(holder, position)
                holder?.centerContent?.setOnClickListener { onClickItem(it, realmResults?.get(position)!!) }
            }
        }
        listAdapter = adapter
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(recycler.context, DividerItemDecoration.VERTICAL_LIST))
        if(posts == null) {
            realm.addChangeListener(realmHandler)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EsaAccessService.getPosts(context, loginProfile).let { context.startService(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.removeChangeListener(realmHandler)
        realm.close()
    }

    fun onClickItem(view: View, post: Post) {
        (activity as? PresenterProvider)?.postListPresenter?.onClickPost(this, post)
                ?: Log.d("Gochisou", "on click post at $view 's ${post.fullName}")
    }

    open fun loadPosts(): RealmResults<Post>? {
        val team: Team? = realm.where(Team::class.java)
            .equalTo("loginToken", loginProfile.token)
            .findFirst()
        return team?.posts?.where()?.findAllSorted("updatedAt", Sort.DESCENDING)
    }

    interface PresenterProvider {
        val postListPresenter: PostListPresenter
    }
}