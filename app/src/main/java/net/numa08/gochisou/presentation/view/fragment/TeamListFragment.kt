package net.numa08.gochisou.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.realm.Realm
import io.realm.RealmConfiguration
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.presentation.service.EsaAccessService
import net.numa08.gochisou.presentation.view.adapter.TeamListAdapter
import org.jetbrains.anko.support.v4.startService
import org.parceler.Parcels
import javax.inject.Inject

class TeamListFragment : ListFragment() {

    lateinit var realmConfiguration: RealmConfiguration
      @Inject set
    lateinit var loginProfileRepository: LoginProfileRepository
      @Inject set

    var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        realm = Realm.getInstance(realmConfiguration)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val teams = realm?.allObjects(Team::class.java)
        val adapter = TeamListAdapter(view!!.context, teams)
        listAdapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginProfileRepository.map { p -> Intent(GochisouApplication.application, EsaAccessService::class.java).let {
                it.putExtra(EsaAccessService.EXTRA_ARG_SERVICE_ACTION, Parcels.wrap(EsaAccessService.ServiceAction.GetTeams(p)))
            }
        }.forEach { activity?.startService(it) }
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Teams"
    }

    override fun onDestroy() {
        realm?.close()
        super.onDestroy()
    }
}
