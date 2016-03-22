package net.numa08.gochisou.presentation.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.kotlin.Either
import net.numa08.gochisou.kotlin.tryAllCatch
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty
import org.parceler.Parcels
import javax.inject.Inject

class EsaAccessService : IntentService(EsaAccessService::class.java.name) {

    companion object {
        val EXTRA_ARG_SERVICE_ACTION = "service_action"

        fun getPosts(context: Context, loginProfile: LoginProfile) : Intent {
            val i = Intent(context, EsaAccessService::class.java)
            i.putExtra(EXTRA_ARG_SERVICE_ACTION, Parcels.wrap(ServiceAction.GetPosts(loginProfile)))
            return i
        }

    }

    lateinit var realmConfiguration: RealmConfiguration
      @Inject set

    lateinit var esaService: EsaService
      @Inject set

    override fun onCreate() {
        super.onCreate()
        GochisouApplication.application?.applicationComponent?.inject(this)
    }

    override fun onHandleIntent(i: Intent?) {
        val action = Parcels.unwrap<ServiceAction>(i?.getParcelableExtra(EXTRA_ARG_SERVICE_ACTION))
        when(action) {
            is ServiceAction.GetPosts -> getPosts(action.loginProfile)
            is ServiceAction.GetTeams -> getTeam(action.loginProfile)
        }
    }

    fun getPosts(loginProfile: LoginProfile) {
        Realm.getInstance(realmConfiguration)
                .use { realm ->
                    val t: Team? = realm.where(Team::class.java)
                            .equalTo("loginToken", loginProfile.token)
                            .findFirst()
                    if(t != null) {
                        val res = tryAllCatch {
                            esaService
                            .posts(loginProfile.tokenForHeader, t.name)
                            .execute()
                        }
                        val right = res as? Either.Right
                        val left = res as? Either.Left
                        right?.value?.body()?.list?.let { l ->
                            realm.executeTransaction {
                                val ul = it.copyToRealmOrUpdate(l)
                                ul.forEach {
                                    if (!(t.posts?.contains(it) ?: false)) {
                                        t.posts?.add(it)
                                    }
                                }
                            }
                        }
                        right?.value?.errorBody()?.let { e ->
                            Log.e("Gochisou", "get http error body $e")
                        }
                        left?.value?.let {
                            Log.e("Gochisou", "Failed request ", it)
                        }
                    } else {
                        getTeam(loginProfile)
                        getPosts(loginProfile)
                    }
                }
    }



    fun getTeam(loginProfile: LoginProfile) {
        val result = tryAllCatch {
            esaService
            .teams(loginProfile.tokenForHeader)
            .execute()
        }
        when(result) {
            is Either.Right -> {
                if(result.value.body() != null) {
                    val list = result.value.body().list?.map{ it.loginToken = loginProfile.token; it }
                    Realm.getInstance(realmConfiguration).use { re ->
                        re.executeTransaction { it.copyToRealmOrUpdate(list) }
                    }
                }
                if(result.value.errorBody() != null) {
                    Log.e("Gochisou", "Failed to get teams ${result.value.errorBody().string()}")
                }
            }
            is Either.Left -> {
                Log.e("Gochisou", "Failed to get teams, because", result.value)
            }
        }
    }

    sealed class ServiceAction(){
        @Parcel(Parcel.Serialization.BEAN)
        class GetPosts @ParcelConstructor constructor(@ParcelProperty("loginProfile") val loginProfile: LoginProfile) : ServiceAction()
        @Parcel(Parcel.Serialization.BEAN)
        class GetTeams @ParcelConstructor constructor(@ParcelProperty("loginProfile") val loginProfile: LoginProfile) : ServiceAction()
    }
}