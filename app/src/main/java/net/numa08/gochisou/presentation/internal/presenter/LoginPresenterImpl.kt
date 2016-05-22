package net.numa08.gochisou.presentation.internal.presenter

import net.numa08.gochisou.data.model.Client
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.presentation.internal.di.PerActivity
import net.numa08.gochisou.presentation.presenter.LoginPresenter
import rx.schedulers.Schedulers
import javax.inject.Inject

@PerActivity
class LoginPresenterImpl @Inject constructor(val esaService: EsaService)
: LoginPresenter {

    override var callback: LoginPresenter.Callback? = null

    override fun login(teamName: String, client: Client, redirectURL: String, code: String) {
        esaService
        .token(
                clientId = client.id,
                clientSecret = client.secret,
                redirectURL = redirectURL,
                code = code)
        .subscribeOn(Schedulers.io())
        .flatMap { t ->
            esaService.teams(t.tokenForHeader).subscribeOn(Schedulers.io())
                    .map { t to it }
        }
        .map { LoginProfile(client = client, token = it.first, team = it.second.list!![0]) to it.second }
        .subscribe(
                { callback?.onLogin(it.first, it.second) },
                { callback?.onFailure(it) }
        )

    }

}