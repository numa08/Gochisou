package net.numa08.gochisou.presentation.internal.presenter

import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.PageNation
import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.presentation.internal.di.PerActivity
import net.numa08.gochisou.presentation.presenter.LoginPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@PerActivity
class LoginPresenterImpl @Inject constructor(val esaService: EsaService)
: LoginPresenter {

    override var callback: LoginPresenter.Callback? = null

    override fun login(profile: LoginProfile) {
        esaService
                .teams(profile.tokenForHeader)
                .enqueue(object : Callback<PageNation.TeamPageNation> {
                    override fun onFailure(call: Call<PageNation.TeamPageNation>?, t: Throwable?) {
                        t?.let {
                            callback?.onFailure(it)
                        }
                    }

                    override fun onResponse(call: Call<PageNation.TeamPageNation>?, response: Response<PageNation.TeamPageNation>?) {
                        response?.body()?.let {
                            callback?.onLogin(profile, it)
                        }
                        response?.errorBody()?.let {
                            callback?.onFailure(Error(it.string()))
                        }
                    }
                })
    }
}