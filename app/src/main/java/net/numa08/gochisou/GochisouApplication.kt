package net.numa08.gochisou

import android.app.Application
import android.support.annotation.VisibleForTesting

import net.numa08.gochisou.presentation.internal.di.components.ApplicationComponent
import net.numa08.gochisou.presentation.internal.di.components.DaggerApplicationComponent
import net.numa08.gochisou.presentation.internal.di.modules.ApplicationModule


class GochisouApplication : Application() {

    @VisibleForTesting
    var applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        application = this
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }

    companion object {

        var application: GochisouApplication? = null

    }
}
