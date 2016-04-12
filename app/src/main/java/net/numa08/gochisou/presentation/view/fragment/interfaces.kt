package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import net.numa08.gochisou.data.model.LoginProfile
import org.parceler.Parcels

interface ArgLoginProfile {
    companion object {
        val ARG_LOGIN_PROFILE = "${ArgLoginProfile::class.qualifiedName}.ARG_LOGIN_PROFILE"
    }

    fun getArguments(): Bundle?
    fun loginProfile(): LoginProfile
            = Parcels.unwrap<LoginProfile>(getArguments()!!.getParcelable(ARG_LOGIN_PROFILE))
}