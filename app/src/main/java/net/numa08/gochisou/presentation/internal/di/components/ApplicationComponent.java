package net.numa08.gochisou.presentation.internal.di.components;

import android.content.Context;

import net.numa08.gochisou.presentation.internal.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
}
