package net.numa08.gochisou.presentation.internal.di.components

import dagger.Subcomponent
import net.numa08.gochisou.presentation.internal.di.PerActivity
import net.numa08.gochisou.presentation.internal.di.modules.LoginPresenterModule
import net.numa08.gochisou.presentation.presenter.InputTeamURLPresenter
import net.numa08.gochisou.presentation.presenter.InputTokenPresenter
import net.numa08.gochisou.presentation.presenter.PostListPresenter

@PerActivity
@Subcomponent(modules = arrayOf(LoginPresenterModule::class, PostListPresenter::class))
interface ActivityComponent {
    fun inputTeamURLPresenter(): InputTeamURLPresenter
    fun inputTokenPresenter(): InputTokenPresenter
    fun postListPresenter(): PostListPresenter
}