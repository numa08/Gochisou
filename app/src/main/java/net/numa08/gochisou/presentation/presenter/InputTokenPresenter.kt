package net.numa08.gochisou.presentation.presenter

import com.trello.rxlifecycle.RxLifecycle
import io.realm.RealmConfiguration
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.PageNation
import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.presentation.internal.di.PerActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

@PerActivity
class InputTokenPresenter @Inject constructor(val realmConfiguration: RealmConfiguration, val service: EsaService/**, val tokenManager: TokenManager */) : Presenter {

    var onLoginHandler: ((PageNation.TeamPageNation) -> Unit)? = null
    var onErrorHandler: ((Throwable) -> Unit)? = null
    private val compositeSubscription = CompositeSubscription()

    override fun resume() {
    }

    override fun pause() {
        compositeSubscription.unsubscribe()
    }

    fun onClickLogin(profile: LoginProfile) {
        compositeSubscription.add(
                service
                        .teams(profile.token)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onLoginHandler, onErrorHandler)
        )
    }
}