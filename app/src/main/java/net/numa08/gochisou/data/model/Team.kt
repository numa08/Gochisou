package net.numa08.gochisou.data.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

@Parcel(Parcel.Serialization.BEAN)
data class Team
@ParcelConstructor
constructor(
        @ParcelProperty("name")
        val name: String,
        @ParcelProperty("privacy")
        val privacy: String,
        @ParcelProperty("description")
        val description: String,
        @ParcelProperty("icon")
        val icon: String,
        @ParcelProperty("url")
        val url: String
)
