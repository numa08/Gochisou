package net.numa08.gochisou

import android.app.Application
import android.support.annotation.VisibleForTesting

import net.numa08.gochisou.presentation.internal.di.components.ApplicationComponent
import net.numa08.gochisou.presentation.internal.di.components.DaggerApplicationComponent
import net.numa08.gochisou.presentation.internal.di.modules.ApplicationModule
import net.numa08.gochisou.presentation.internal.di.modules.LoginProfileRepositoryModule
import net.numa08.gochisou.presentation.internal.di.modules.NavigationIdentifierRepositoryModule
import org.jetbrains.anko.defaultSharedPreferences


class GochisouApplication : Application() {

    @VisibleForTesting
    var applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        application = this
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .loginProfileRepositoryModule(LoginProfileRepositoryModule(defaultSharedPreferences))
                .navigationIdentifierRepositoryModule(NavigationIdentifierRepositoryModule(defaultSharedPreferences))
                .build()
    }

    companion object {

        var application: GochisouApplication? = null

    }
}
