package net.numa08.gochisou.data.model

import io.realm.RealmObject

class Team(
        var name: String? = null,
        var privacy: String? = null,
        var description: String? = null,
        var icon: String? = null,
        var url: String? = null
) : RealmObject() {}
