package net.numa08.gochisou.presentation.presenter

import android.support.v4.app.FragmentManager
import android.util.Log
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.presentation.internal.di.PerActivity
import net.numa08.gochisou.presentation.view.activity.LoginActivity
import net.numa08.gochisou.presentation.view.fragment.InputTokenFragment
import org.jetbrains.anko.support.v4.withArguments
import org.parceler.Parcels

@PerActivity
class InputTeamURLPresenter : Presenter {

    var fragmentManager: FragmentManager? = null

    override fun resume() {}

    override fun pause() {}

    fun onClickNext(profile: LoginProfile) {
        val fragment = InputTokenFragment().withArguments(
                InputTokenFragment.ARG_LOGIN_PROFILE to Parcels.wrap(profile)
        )
        fragmentManager
        ?.beginTransaction()
        ?.replace(R.id.content, fragment, LoginActivity.BACK_STACK)
        ?.addToBackStack(LoginActivity.BACK_STACK)
        ?.commit()
        ?: Log.d("Gochisou", "Please set fragmentManager")
    }
}