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
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.data.service.EsaService
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty
import org.parceler.Parcels
import rx.schedulers.Schedulers
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
            is ServiceAction.GetToken -> getToken(action.client, action.redirectURL, action.code)
        }
    }

    fun getToken(client: Client, redirectURL: String, code: String) {
        esaService.token(
                clientId = client.id,
                clientSecret = client.secret,
                redirectURL = redirectURL,
                code = code)
        .subscribeOn(Schedulers.io())
        .flatMap { t ->
            esaService.teams(t.tokenForHeader).subscribeOn(Schedulers.io())
            .map { t to it }
        }
        .map { LoginProfile(client = client, token = it.first, team = it.second.list!![0]) }
        .subscribe(
                {loginProfileRepository.add(it)},
                {Log.e(GochisouApplication.LOG_TAG, "get token error", it)})
    }

    fun getPosts(loginProfile: LoginProfile, query: String? = null) {
        esaService
        .posts(
                token = loginProfile.tokenForHeader,
                teamName = loginProfile.team.name,
                query = query)
        .map { it.list?.map { it.teamName = loginProfile.team.name; it }}
        .subscribe(
                { l ->
                    Realm.getInstance(realmConfiguration).use { r ->
                    r.executeTransaction { it.copyToRealmOrUpdate(l) }
                }},
                {Log.e(GochisouApplication.LOG_TAG, "get posts error", it)})

    }



    fun getTeam(loginProfile: LoginProfile) {
        esaService
        .teams(loginProfile.tokenForHeader)
        .subscribeOn(Schedulers.io())
        .map { loginProfile.copy(team = it.list!![0]) }
        .subscribe(
                {loginProfileRepository.updateOrSet(it)} ,
                {Log.e(GochisouApplication.LOG_TAG, "get team error", it)})
    }

    fun getMember(loginProfile: LoginProfile) {
        esaService
        .members(
                token = loginProfile.tokenForHeader,
                teamName = loginProfile.team.name)
        .subscribeOn(Schedulers.io())
        .map { it.list?.map { it.teamName = loginProfile.team.name; it } }
        .subscribe(
                {l ->
                    Realm.getInstance(realmConfiguration).use { r ->
                        r.executeTransaction { it.copyToRealmOrUpdate(l) }
                    }
                },
                {Log.e(GochisouApplication.LOG_TAG, "get member err", it)})
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