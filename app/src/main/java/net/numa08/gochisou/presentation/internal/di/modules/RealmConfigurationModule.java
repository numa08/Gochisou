package net.numa08.gochisou.presentation.internal.di.modules;

import android.content.Context;

import net.numa08.gochisou.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

@Module
public class RealmConfigurationModule {

    @Provides
    @PerActivity
    public RealmConfiguration providesRealmConfiguration(Context context) {
        return new RealmConfiguration.Builder(context.getCacheDir())
                .name("esa")
                .deleteRealmIfMigrationNeeded()
                .build();
    }

}
