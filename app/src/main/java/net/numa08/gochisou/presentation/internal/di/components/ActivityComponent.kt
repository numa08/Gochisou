package net.numa08.gochisou.presentation.internal.di.components

import dagger.Subcomponent
import net.numa08.gochisou.presentation.internal.di.PerActivity
import net.numa08.gochisou.presentation.internal.presenter.LoginPresenterImpl
import net.numa08.gochisou.presentation.presenter.PostListPresenter

@PerActivity
@Subcomponent(modules = arrayOf(LoginPresenterImpl::class, PostListPresenter::class))
interface ActivityComponent {
    fun loginPresenter(): LoginPresenterImpl
    fun postListPresenter(): PostListPresenter
}