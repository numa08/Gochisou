package net.numa08.gochisou.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmString
import io.realm.annotations.PrimaryKey
import java.util.*

open class Post(
        var number: Long = 0,
        var name: String? = null,
        var tags: RealmList<RealmString>? = null,
        var category: String? = null,
        @PrimaryKey
        var fullName: String? = null,
        @SerializedName("wip")
        var isWip: Boolean = false,
        @SerializedName("body_md")
        var bodyMD: String? = null,
        @SerializedName("body_html")
        var bodyHTML: String? = null,
        var createdAt: Date? = null,
        var updatedAt: Date? = null,
        var message: String? = null,
        var revisionNumber: Long = 0,
        var createdBy: User? = null,
        var updatedBy: User? = null,
        var kind: String? = null,
        var commentsCount: Long = 0,
        var tasksCount: Long = 0,
        var doneTasksCount: Long = 0,
        var stargazersCount: Long = 0,
        var watchersCount: Long = 0,
        @SerializedName("star")
        var isStar: Boolean = false,
        @SerializedName("watch")
        var isWatch: Boolean = false
) : RealmObject() {}