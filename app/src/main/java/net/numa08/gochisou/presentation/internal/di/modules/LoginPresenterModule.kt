package net.numa08.gochisou.presentation.internal.di.modules

import dagger.Module
import dagger.Provides
import net.numa08.gochisou.presentation.presenter.InputTeamURLPresenter

@Module
class LoginPresenterModule {

    @Provides
    fun provideInputTeamURLPresenter() = InputTeamURLPresenter()

}