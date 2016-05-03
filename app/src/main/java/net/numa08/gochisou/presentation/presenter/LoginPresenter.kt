package net.numa08.gochisou.presentation.presenter

import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.PageNation

interface LoginPresenter {

    interface Callback {
        fun onLogin(loginProfile: LoginProfile, team: PageNation.TeamPageNation)
        fun onFailure(throwable: Throwable)
    }

    var callback: Callback?

    fun login(profile: LoginProfile)

}