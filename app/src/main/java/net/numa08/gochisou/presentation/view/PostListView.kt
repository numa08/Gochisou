package net.numa08.gochisou.presentation.view

import net.numa08.gochisou.data.model.Post

interface PostListView {
    fun showPost(post: Post)
}