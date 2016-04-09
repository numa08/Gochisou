package net.numa08.gochisou.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Member(
        @PrimaryKey
        @SerializedName("screen_name")
        var screenName: String? = null,
        var name: String? = null,
        var icon: String? = null,
        @Suppress("unused")
        var email: String? = null
) : RealmObject()