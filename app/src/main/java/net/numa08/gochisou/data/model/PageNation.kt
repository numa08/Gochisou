package net.numa08.gochisou.data.model

import com.google.gson.annotations.SerializedName

sealed class PageNation<T> {
    open val list: List<T>? = null
    @SerializedName("prev_page")
    val prevPage: Int = 0
    @SerializedName("next_page")
    val nextPage: Int = 0
    @SerializedName("total_count")
    val totalCount: Int = 0

    class TeamPageNation : PageNation<Team>() {
        @SerializedName("teams")
        override val list: List<Team>? = null
    }

    class PostPageNation : PageNation<Post>() {
        @SerializedName("posts")
        override val list: List<Post>? = null
    }

    class MemberPageNation : PageNation<Member>() {
        @SerializedName("members")
        override val list: List<Member>? = null
    }
}
