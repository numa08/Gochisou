package net.numa08.gochisou.data.model

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

@Parcel(Parcel.Serialization.BEAN)
data class Token
@ParcelConstructor
constructor(
        @ParcelProperty("accessToken")
        @SerializedName("access_token")
        val accessToken: String,
        @ParcelProperty("tokenType")
        @SerializedName("token_type")
        val tokenType: String,
        @ParcelProperty("scope")
        val scope: String,
        @ParcelProperty("createdAt")
        @SerializedName("created_at")
        val createdAt: Long
) {
        val tokenForHeader : String
                get() = "Bearer $accessToken"

}