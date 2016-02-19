package net.numa08.gochisou.presentation.service

import android.util.Log

import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.data.model.PageNation
import net.numa08.gochisou.data.model.Team

import org.androidannotations.annotations.EIntentService
import org.androidannotations.annotations.ServiceAction
import org.androidannotations.api.support.app.AbstractIntentService

import javax.inject.Inject

import io.realm.Realm
import io.realm.RealmConfiguration
import rx.Observable
import rx.Observer
import rx.schedulers.Schedulers

@EIntentService
open class EsaAccessService : AbstractIntentService(EsaAccessService::class.java.name) {

    var realmConfiguration: RealmConfiguration? = null
      @Inject set
    var esaService: EsaService? = null
      @Inject set

    @ServiceAction
    open fun loadTeam(token: String) {
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
