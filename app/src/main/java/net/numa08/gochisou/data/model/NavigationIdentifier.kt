package net.numa08.gochisou.data.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class NavigationIdentifier(var name: String = "", var avatar: String = "", var loginProfile: LoginProfile? = LoginProfile(), var type: Type = NavigationIdentifier.Type.INVALID) {
    @Parcel
    enum class Type @ParcelConstructor constructor() {
        POST,
        INVALID
    }
}