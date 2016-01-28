package net.numa08.gochisou.presentation.internal.di.modules;

import net.numa08.gochisou.data.repository.RealmEsaRepository;
import net.numa08.gochisou.data.repository.RetrofitEsaRepository;
import net.numa08.gochisou.domain.usecase.LoadPostUseCase;
import net.numa08.gochisou.domain.usecase.LoadPostUseCaseImpl_Factory;
import net.numa08.gochisou.domain.usecase.SavePostUseCase;
import net.numa08.gochisou.domain.usecase.SavePostUserCaseImpl_Factory;
import net.numa08.gochisou.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class UpdateNewPostModule {

    @Provides
    @PerActivity
    public LoadPostUseCase providesLoadPostUseCase(RetrofitEsaRepository repository) {
        return LoadPostUseCaseImpl_Factory.create(() -> repository).get();
    }

    @Provides
    @PerActivity
    public SavePostUseCase providesSavePostUseCase(RealmEsaRepository repository) {
        return SavePostUserCaseImpl_Factory.create(() -> repository).get();
    }
}
