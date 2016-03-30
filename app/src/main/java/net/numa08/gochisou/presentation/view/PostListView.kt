package net.numa08.gochisou.presentation.view

import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.presentation.view.fragment.PostListFragment

interface PostListView {
    fun showPost(fragment: PostListFragment, post: Post)
}