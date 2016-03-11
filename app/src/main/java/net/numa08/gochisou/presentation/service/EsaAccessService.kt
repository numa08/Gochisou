package net.numa08.gochisou.presentation.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.data.service.EsaService
import rx.schedulers.Schedulers
import javax.inject.Inject

class EsaAccessService : IntentService(EsaAccessService::class.java.name) {
    override fun onHandleIntent(p0: Intent?) {}

    var realmConfiguration: RealmConfiguration? = null
      @Inject set
    var esaService: EsaService? = null
      @Inject set

    fun loadTeam(token: String) {
        esaService
        ?.teams(token)
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(Schedulers.io())
        ?.map { it.list ?: emptyList<Team>() }
        ?.subscribe({ tl ->
            Realm.getInstance(realmConfiguration).use {
                it.executeTransaction { it.copyToRealmOrUpdate(tl) }
            }
        } ,{
            Log.e("Gochisou", "load team error ", it)
        })
    }

}
