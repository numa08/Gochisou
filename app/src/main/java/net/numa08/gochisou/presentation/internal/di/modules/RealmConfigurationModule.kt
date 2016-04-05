package net.numa08.gochisou.presentation.internal.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
@Singleton
class RealmConfigurationModule {

    @Provides
    @Singleton
    fun providesRealmConfiguration(context: Context): RealmConfiguration {
        return RealmConfiguration.Builder(context.cacheDir).name("esa").deleteRealmIfMigrationNeeded().build()
    }

}
