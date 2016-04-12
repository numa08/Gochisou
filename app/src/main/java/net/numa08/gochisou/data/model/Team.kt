package net.numa08.gochisou.data.model

import com.google.gson.annotations.Expose
import io.realm.Realm
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
        var posts: RealmList<Post>? = null,
        @Expose
        var members: RealmList<Member>? = null
) : RealmObject() {
    companion object {
        fun findByLoginProfile(realm: Realm, loginProfile: LoginProfile): Team? =
                realm.where(Team::class.java)
                        .equalTo("loginToken", loginProfile.token)
                        .findFirst()
    }
}
