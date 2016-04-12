package net.numa08.gochisou.presentation.view.activity

import android.content.Intent
import net.numa08.gochisou.data.model.LoginProfile
import org.parceler.Parcels

interface IntentLoginProfile {
    companion object {
        val ARG_LOGIN_PROFILE = "${IntentLoginProfile::class.qualifiedName}.ARG_LOGIN_PROFILE"
    }

    fun getIntent(): Intent?
    fun loginProfile(): LoginProfile
            = Parcels.unwrap<LoginProfile>(getIntent()!!.getParcelableExtra(ARG_LOGIN_PROFILE))
}