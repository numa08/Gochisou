package net.numa08.gochisou.presentation.internal.di.components

import android.content.Context
import dagger.Component
import net.numa08.gochisou.presentation.internal.di.PerActivity
import net.numa08.gochisou.presentation.internal.di.modules.ApplicationModule
import net.numa08.gochisou.presentation.internal.di.modules.EsaServiceModule
import net.numa08.gochisou.presentation.internal.di.modules.LoginPresenterModule
import net.numa08.gochisou.presentation.internal.di.modules.RealmConfigurationModule
import net.numa08.gochisou.presentation.view.activity.LoginActivity
import net.numa08.gochisou.presentation.view.fragment.LoginFragment
import net.numa08.gochisou.presentation.view.fragment.TeamListFragment

@PerActivity
@Component(modules = arrayOf(ApplicationModule::class, EsaServiceModule::class, RealmConfigurationModule::class, LoginPresenterModule::class))
interface ApplicationComponent {
    fun context(): Context

    fun inject(fragment: LoginFragment)

    fun inject(fragment: TeamListFragment)

    fun inject(activity: LoginActivity)
}
