package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_member_list.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.Member
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.presentation.service.EsaAccessService
import net.numa08.gochisou.presentation.view.adapter.MemberListAdapter
import net.numa08.gochisou.presentation.widget.DividerItemDecoration
import org.parceler.Parcels
import javax.inject.Inject

class MemberListFragment : Fragment() {

    companion object {
        val ARG_LOGIN_PROFILE = "${MemberListFragment::class.simpleName}.ARG_LOGIN_PROFILE"
    }

    lateinit var realmConfiguration: RealmConfiguration
        @Inject set
    val realm by lazy { Realm.getInstance(realmConfiguration) }
    val loginProfile by lazy { Parcels.unwrap<LoginProfile>(arguments!!.getParcelable(ARG_LOGIN_PROFILE)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EsaAccessService.getMembers(context, loginProfile).let { context.startService(it) }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_member_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.addItemDecoration(DividerItemDecoration(view?.context, DividerItemDecoration.VERTICAL_LIST))
        val adapter = MemberListAdapter(loadMembers(), Picasso.with(view?.context))
        recycler.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun loadMembers(): RealmResults<Member>? {
        val team: Team? = realm.where(Team::class.java)
                .equalTo("loginToken", loginProfile.token)
                .findFirst()
        return team?.members?.where()?.findAll()
    }
}