package net.numa08.gochisou.data.model

import android.os.Parcel
import android.os.Parcelable

data class LoginProfile(var teamURL: String = "", var token: String = "") : Parcelable {
    constructor(source: Parcel): this(source.readString(), source.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(teamURL)
        dest?.writeString(token)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<LoginProfile> = object : Parcelable.Creator<LoginProfile> {
            override fun createFromParcel(source: Parcel): LoginProfile {
                return LoginProfile(source)
            }

            override fun newArray(size: Int): Array<LoginProfile?> {
                return arrayOfNulls(size)
            }
        }
    }
}