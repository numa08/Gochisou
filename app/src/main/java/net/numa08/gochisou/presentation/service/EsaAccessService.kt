package net.numa08.gochisou.presentation.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.data.model.Client
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.data.repositories.LoginProfileRepository
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

        fun getPosts(context: Context, loginProfile: LoginProfile, query: String? = null): Intent =
                Intent(context, EsaAccessService::class.java).apply {
                    putExtra(EXTRA_ARG_SERVICE_ACTION, Parcels.wrap(ServiceAction.GetPosts(loginProfile, query)))
                }

        fun getMembers(context: Context, loginProfile: LoginProfile): Intent =
                Intent(context, EsaAccessService::class.java).apply {
                    putExtra(EXTRA_ARG_SERVICE_ACTION, Parcels.wrap(ServiceAction.GetMembers(loginProfile)))
                }

        fun getToken(context: Context, teamName: String, client: Client, redirectURL: String, code: String): Intent =
                Intent(context, EsaAccessService::class.java).apply {
                    putExtra(EXTRA_ARG_SERVICE_ACTION, Parcels.wrap(ServiceAction.GetToken(teamName, client, redirectURL, code)))
                }
    }

    lateinit var loginProfileRepository: LoginProfileRepository
        @Inject set
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
            is ServiceAction.GetPosts -> getPosts(action.loginProfile, action.query)
            is ServiceAction.GetTeams -> getTeam(action.loginProfile)
            is ServiceAction.GetMembers -> getMember(action.loginProfile)
            is ServiceAction.GetToken -> getToken(action.teamName, action.client, action.redirectURL, action.code)
        }
    }

    fun getToken(teamName: String, client: Client, redirectURL: String, code: String) {
        val result = tryAllCatch {
            esaService.token(
                    clientId = client.id,
                    clientSecret = client.secret,
                    redirectURL = redirectURL,
                    code = code
            ).execute()
        }
        when (result) {
            is Either.Right -> {
                result.value?.body()?.let {
                    val profile = LoginProfile(teamName, client, it)
                    getTeam(profile)
                    loginProfileRepository.add(profile)
                }
                result.value?.errorBody()?.let {
                    Log.e("Gochisou", "could not get token", Error(it.string()))
                }
            }
            is Either.Left -> {
                Log.e("Gochisou", "could not get token ", result.value)
            }
        }
    }

    fun getPosts(loginProfile: LoginProfile, query: String? = null) {
        Realm.getInstance(realmConfiguration)
                .use { realm ->
                    val t: Team? = realm.where(Team::class.java)
                            .equalTo("loginToken", loginProfile.token.accessToken)
                            .findFirst()
                    if(t != null) {
                        val res = tryAllCatch {
                            esaService
                                    .posts(loginProfile.tokenForHeader, t.name, query)
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
                    val list = result.value.body().list?.map { it.loginToken = loginProfile.token.accessToken; it }
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

    fun getMember(loginProfile: LoginProfile) {
        Realm.getInstance(realmConfiguration).use { realm ->
            val t: Team? = realm.where(Team::class.java)
                    .equalTo("loginToken", loginProfile.token.accessToken)
                    .findFirst()
            if (t != null) {
                val res = tryAllCatch {
                    esaService
                            .members(loginProfile.tokenForHeader, t.name)
                            .execute()
                }
                val right = res as? Either.Right
                val left = res as? Either.Left
                right?.value?.body()?.list?.let { l ->
                    realm.executeTransaction {
                        val ul = it.copyToRealmOrUpdate(l)
                        ul.forEach {
                            if (!(t.members?.contains(it) ?: false)) {
                                t.members?.add(it)
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
                getMember(loginProfile)
            }
        }
    }

    sealed class ServiceAction(){
        @Parcel(Parcel.Serialization.BEAN)
        class GetPosts @ParcelConstructor constructor(@ParcelProperty("loginProfile") val loginProfile: LoginProfile, @ParcelProperty("query") val query: String? = "") : ServiceAction()
        @Parcel(Parcel.Serialization.BEAN)
        class GetTeams @ParcelConstructor constructor(@ParcelProperty("loginProfile") val loginProfile: LoginProfile) : ServiceAction()
        @Parcel(Parcel.Serialization.BEAN)
        class GetMembers @ParcelConstructor constructor(@ParcelProperty("loginProfile") val loginProfile: LoginProfile) : ServiceAction()

        @Parcel(Parcel.Serialization.BEAN)
        class GetToken @ParcelConstructor constructor(@ParcelProperty("teamName") val teamName: String, @ParcelProperty("client") val client: Client, @ParcelProperty("redirectURL") val redirectURL: String, @ParcelProperty("code") val code: String) : ServiceAction()
    }
}