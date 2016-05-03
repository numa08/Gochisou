package net.numa08.gochisou.data.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

@Parcel(Parcel.Serialization.BEAN)
data class LoginProfile
@ParcelConstructor
constructor(@ParcelProperty("teamURL") val teamURL: String,
            @ParcelProperty("token") val token: String) {

    val tokenForHeader : String
        get() = "Bearer $token"
}