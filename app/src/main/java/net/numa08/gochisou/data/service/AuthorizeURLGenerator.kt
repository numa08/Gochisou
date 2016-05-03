package net.numa08.gochisou.data.service

import android.net.Uri
import android.text.TextUtils

class AuthorizeURLGenerator {

    companion object {
        val ENDPOINT = "api.esa.io"
    }

    fun generateAuthorizeURL(clientID: String, redirectURL: String, scope: String = "write read", responseType: String = "code", state: String = ""): String
            = Uri.Builder()
            .scheme("https")
            .authority(ENDPOINT)
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", clientID)
            .appendQueryParameter("redirect_uri", redirectURL)
            .appendQueryParameter("scope", scope)
            .appendQueryParameter("response_type", responseType)
            .apply {
                if (!TextUtils.isEmpty(state)) {
                    appendQueryParameter("state", state)
                }
            }.build().toString()

}