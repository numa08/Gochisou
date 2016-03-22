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
        @SerializedName("full_name")
        @PrimaryKey
        var fullName: String? = null,
        var isWip: Boolean = false,
        @SerializedName("body_md")
        var bodyMD: String? = null,
        @SerializedName("body_html")
        var bodyHTML: String? = null,
        var createdAt: Date? = null,
        var updateAt: Date? = null,
        var message: String? = null,
        @SerializedName("revision_number")
        var revisionNumber: Long = 0,
        @SerializedName("created_by")
        var createdBy: User? = null,
        @SerializedName("update_by")
        var updateBy: User? = null,
        var kind: String? = null,
        @SerializedName("comment_count")
        var commentCount: Long = 0,
        @SerializedName("tasks_count")
        var tasksCount: Long = 0,
        @SerializedName("stargazers_count")
        var stargazersCount: Long = 0,
        @SerializedName("watchers_count")
        var watchersCount: Long = 0,
        var isStar: Boolean = false,
        var isWatch: Boolean = false
) : RealmObject() {}