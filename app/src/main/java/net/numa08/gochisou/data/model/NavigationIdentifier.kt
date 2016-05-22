package net.numa08.gochisou.data.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty

sealed class NavigationIdentifier(val name: String, val avatar: String, val loginProfile: LoginProfile) {

    @Parcel(Parcel.Serialization.BEAN)
    class PostNavigationIdentifier @ParcelConstructor constructor(
            @ParcelProperty("name") name: String,
            @ParcelProperty("avatar") avatar: String,
            @ParcelProperty("loginProfile") loginProfile: LoginProfile
    ) : NavigationIdentifier(name, avatar, loginProfile)

    @Parcel(Parcel.Serialization.BEAN)
    class PostDetailNavigationIdentifier @ParcelConstructor constructor(
            @ParcelProperty("name") name: String,
            @ParcelProperty("avatar") avatar: String,
            @ParcelProperty("loginProfile") loginProfile: LoginProfile,
            @ParcelProperty("fullName") val fullName: String
    ) : NavigationIdentifier(name, avatar, loginProfile) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other?.javaClass != javaClass) return false
            if (!super.equals(other)) return false

            other as PostDetailNavigationIdentifier

            if (fullName != other.fullName) return false

            return true
        }

        override fun hashCode(): Int {
            var result = super.hashCode()
            result += 31 * result + fullName.hashCode()
            return result
        }

        override fun toString(): String{
            return "PostDetailNavigationIdentifier(fullName='$fullName')"
        }


    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as NavigationIdentifier

        if (name != other.name) return false
        if (avatar != other.avatar) return false
        if (loginProfile != other.loginProfile) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result += 31 * result + avatar.hashCode()
        result += 31 * result + loginProfile.hashCode()
        return result
    }

    override fun toString(): String{
        return "NavigationIdentifier(name='$name', avatar='$avatar', loginProfile=$loginProfile)"
    }

}
