package net.numa08.gochisou.presentation.internal.di.modules;

import android.content.Context;

import net.numa08.gochisou.GochisouApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class ApplicationModule {

    private final GochisouApplication application;

    public ApplicationModule(GochisouApplication application) {
        this.application = application;
    }

    @Provides
    public Context providesContext() {
        return application;
    }
}
