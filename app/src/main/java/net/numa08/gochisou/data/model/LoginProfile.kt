package net.numa08.gochisou.data.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

@Parcel(Parcel.Serialization.BEAN)
data class LoginProfile
@ParcelConstructor
constructor(
        @ParcelProperty("client")
        val client: Client,
        @ParcelProperty("token")
        val token: Token,
        @ParcelProperty("team")
        val team: Team) {

    val tokenForHeader : String
        get() = "Bearer ${token.accessToken}"
}