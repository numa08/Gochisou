package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.View
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.presentation.service.EsaAccessService
import net.numa08.gochisou.presentation.view.adapter.PostListAdapter
import org.parceler.Parcels
import javax.inject.Inject

class PostListFragment : ListFragment() {

    companion object {
        val ARG_LOGIN_PROFILE = "${PostListFragment::class.simpleName}.ARG_LOGIN_PROFILE"
    }

    lateinit var realmConfiguration: RealmConfiguration
        @Inject set


    val loginProfile by lazy { Parcels.unwrap<LoginProfile>(arguments!!.getParcelable(ARG_LOGIN_PROFILE)) }

    val realm by lazy { Realm.getInstance(realmConfiguration) }
    val realmHandler by lazy {{
        listAdapter = loadPosts()?.let { PostListAdapter(context, it) }
    }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = loadPosts()?.let { PostListAdapter(context, it) }
        listAdapter = adapter
        if(listAdapter == null) {
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

    private fun loadPosts() : RealmResults<Post>?  {
        val team: Team? = realm.where(Team::class.java)
            .equalTo("loginToken", loginProfile.token)
            .findFirst()
        when(team?.posts?.isValid) {
            true -> return team?.posts?.where()?.findAllSorted("updateAt", Sort.DESCENDING)
        }
        return null
    }
}