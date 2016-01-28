package net.numa08.gochisou.presentation.internal.di.modules;

import net.numa08.gochisou.data.repository.RealmEsaRepository;
import net.numa08.gochisou.domain.repository.EsaRepository;
import net.numa08.gochisou.domain.usecase.LoadPostUseCase;
import net.numa08.gochisou.domain.usecase.LoadPostUseCaseImpl;
import net.numa08.gochisou.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class RealmLoadPostsUseCaseModule {

    @Provides
    @PerActivity
    public EsaRepository providesEsaRepository(RealmEsaRepository repository) {
        return repository;
    }

    @Provides
    @PerActivity
    public LoadPostUseCase providesLoadPostUseCase(LoadPostUseCaseImpl impl) {
        return impl;
    }

}
