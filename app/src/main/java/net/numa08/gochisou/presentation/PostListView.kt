package net.numa08.gochisou.presentation

import net.numa08.gochisou.data.model.Post

interface PostListView {

    fun renderPostList(posts: Collection<Post>)

    fun showPost(post: Post)

}
