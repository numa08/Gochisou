package net.numa08.gochisou.presentation.internal.di.modules

import android.content.Context

import net.numa08.gochisou.GochisouApplication

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
@Singleton
class ApplicationModule(private val application: GochisouApplication) {

    @Provides
    fun providesContext(): Context {
        return application
    }
}
