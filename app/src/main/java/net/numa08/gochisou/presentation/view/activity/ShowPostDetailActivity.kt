package net.numa08.gochisou.presentation.view.activity

import android.app.Activity
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.presentation.view.fragment.PostListFragment
import org.jetbrains.anko.intentFor
import org.parceler.Parcels

fun Activity.showPostDetail(fragment: PostListFragment, post: Post) {
    startActivity(intentFor<PostDetailActivity>(
            "fullName" to (post.fullName ?: ""),
            "loginProfile" to Parcels.wrap(fragment.loginProfile)))
}