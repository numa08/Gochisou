package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.View
import io.realm.Realm
import io.realm.RealmConfiguration
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.presentation.view.adapter.TeamListAdapter
import org.androidannotations.annotations.EFragment
import javax.inject.Inject

@EFragment
open class TeamListFragment : ListFragment() {

    var realmConfiguration: RealmConfiguration? = null
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

    override fun onDestroy() {
        realm?.close()
        super.onDestroy()
    }
}
