package net.numa08.gochisou.presentation.internal.di.modules

import dagger.Module
import dagger.Provides
import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.presentation.internal.di.PerActivity
import net.numa08.gochisou.presentation.presenter.InputTeamURLPresenter
import net.numa08.gochisou.presentation.presenter.InputTokenPresenter
import net.numa08.gochisou.presentation.presenter.PostListPresenter

@Module
@PerActivity
class LoginPresenterModule {

    @Provides
    @PerActivity
    fun provideInputTeamURLPresenter() = InputTeamURLPresenter()

    @Provides
    @PerActivity
    fun provideInputTokenPresenter(esaService: EsaService) = InputTokenPresenter(esaService)
}

@Module
@PerActivity
class PostListPresenterModule {

    @Provides
    @PerActivity
    fun providesPostListPresenter() = PostListPresenter()

}