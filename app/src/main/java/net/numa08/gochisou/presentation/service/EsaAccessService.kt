package net.numa08.gochisou.presentation.service

import android.app.IntentService
import android.content.Intent
import io.realm.RealmConfiguration
import net.numa08.gochisou.data.service.EsaService
import javax.inject.Inject

class EsaAccessService : IntentService(EsaAccessService::class.java.name) {
    override fun onHandleIntent(p0: Intent?) {}

    var realmConfiguration: RealmConfiguration? = null
      @Inject set
    var esaService: EsaService? = null
      @Inject set

}
