package net.numa08.gochisou.presentation.internal.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import net.numa08.gochisou.GochisouApplication
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Singleton

@Module
@Singleton
class ApplicationModule(private val application: GochisouApplication) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context) : SharedPreferences = context.defaultSharedPreferences
}
