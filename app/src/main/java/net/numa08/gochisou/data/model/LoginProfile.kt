package net.numa08.gochisou.data.model

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
data class LoginProfile(var teamURL: String = "", var token: String = "") {
    val tokenForHeader : String
        get() = "Bearer $token"
}