package net.numa08.gochisou.presentation.presenter

import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.presentation.internal.di.PerActivity
import net.numa08.gochisou.presentation.view.PostListView
import javax.inject.Inject

@PerActivity
class PostListPresenter @Inject constructor() : Presenter {

    var postListView: PostListView? = null

    override fun resume() {
    }

    override fun pause() {
    }

    fun onClickPost(post: Post) {
        postListView?.showPost(post)
    }

}