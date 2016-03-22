package net.numa08.gochisou.data.model

import com.google.gson.annotations.Expose
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Team(
        @PrimaryKey
        var name: String? = null,
        var privacy: String? = null,
        var description: String? = null,
        var icon: String? = null,
        var url: String? = null,
        @Expose
        var loginToken: String? = null,
        @Expose
        var posts: RealmList<Post>? = RealmList()
) : RealmObject() {}
