package net.numa08.gochisou.data.model

import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

@Parcel(Parcel.Serialization.BEAN)
data class TokenInfo
@ParcelConstructor
constructor(
        @ParcelProperty("resourceOwnerId")
        @SerializedName("resource_owner_id")
        val resourceOwnerId: Long,
        @ParcelProperty("scopes")
        val scopes: List<String>,
        @ParcelProperty("expiresInSeconds")
        @SerializedName("expires_in_seconds")
        val expiresInSeconds: Long?,
        @ParcelProperty("application")
        val application: ApplicationInfo,
        @ParcelProperty("createdAt")
        @SerializedName("created_at")
        val createdAt: Long
)