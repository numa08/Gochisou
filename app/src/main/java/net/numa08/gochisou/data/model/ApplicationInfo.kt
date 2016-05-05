package net.numa08.gochisou.data.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

@Parcel(Parcel.Serialization.BEAN)
data class ApplicationInfo
@ParcelConstructor
constructor(
        @ParcelProperty("uid")
        val uid: String
)