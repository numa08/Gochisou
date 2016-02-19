package net.numa08.gochisou.presentation.internal.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import net.numa08.gochisou.presentation.internal.di.PerActivity

@Module
class RealmConfigurationModule {

    @Provides
    @PerActivity
    fun providesRealmConfiguration(context: Context): RealmConfiguration {
        return RealmConfiguration.Builder(context.cacheDir).name("esa").deleteRealmIfMigrationNeeded().build()
    }

}
