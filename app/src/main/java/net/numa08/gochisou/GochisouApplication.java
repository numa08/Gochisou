package net.numa08.gochisou;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import net.numa08.gochisou.presentation.internal.di.components.ApplicationComponent;
import net.numa08.gochisou.presentation.internal.di.components.DaggerApplicationComponent;
import net.numa08.gochisou.presentation.internal.di.modules.ApplicationModule;


public class GochisouApplication extends Application {

    private static GochisouApplication self;

    public static GochisouApplication application() {
        return self;
    }

    @VisibleForTesting
    public ApplicationComponent applicationComponent;

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
