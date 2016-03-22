package net.numa08.gochisou.presentation.presenter

import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.PageNation
import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.presentation.internal.di.PerActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@PerActivity
class InputTokenPresenter @Inject constructor(val service: EsaService) : Presenter {

    var onLoginHandler: ((Call<PageNation.TeamPageNation>? ,LoginProfile, Response<PageNation.TeamPageNation>?) -> Unit)? = null
    var onErrorHandler: ((Call<PageNation.TeamPageNation>? ,Throwable?) -> Unit)? = null

    override fun resume() {
    }

    override fun pause() {
    }

    fun onClickLogin(profile: LoginProfile) {
        service
            .teams(profile.tokenForHeader)
            .enqueue(object :Callback<PageNation.TeamPageNation> {
                override fun onFailure(call: Call<PageNation.TeamPageNation>?, t: Throwable?) {
                    onErrorHandler?.invoke(call, t)
                }

                override fun onResponse(call: Call<PageNation.TeamPageNation>?, response: Response<PageNation.TeamPageNation>?) {
                    onLoginHandler?.invoke(call, profile, response)
                }

            })
    }
}