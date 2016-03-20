package net.numa08.gochisou.presentation.internal.di.modules

import dagger.Module
import dagger.Provides
import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.presentation.presenter.InputTeamURLPresenter
import net.numa08.gochisou.presentation.presenter.InputTokenPresenter

@Module
class LoginPresenterModule {

    @Provides
    fun provideInputTeamURLPresenter() = InputTeamURLPresenter()

    @Provides
    fun provideInputTokenPresenter(esaService: EsaService) = InputTokenPresenter(esaService)
}
