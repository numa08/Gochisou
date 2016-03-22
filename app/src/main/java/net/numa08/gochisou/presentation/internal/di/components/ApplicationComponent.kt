package net.numa08.gochisou.presentation.internal.di.components

import android.content.Context
import dagger.Component
import net.numa08.gochisou.presentation.internal.di.PerActivity
import net.numa08.gochisou.presentation.internal.di.modules.*
import net.numa08.gochisou.presentation.service.EsaAccessService
import net.numa08.gochisou.presentation.view.activity.LoginActivity
import net.numa08.gochisou.presentation.view.activity.MainActivity
import net.numa08.gochisou.presentation.view.fragment.MainNavigationFragment
import net.numa08.gochisou.presentation.view.fragment.PostListFragment
import net.numa08.gochisou.presentation.view.fragment.TeamListFragment

@PerActivity
@Component(modules = arrayOf(ApplicationModule::class, EsaServiceModule::class, RealmConfigurationModule::class, LoginPresenterModule::class, LoginProfileRepositoryModule::class, NavigationIdentifierRepositoryModule::class))
interface ApplicationComponent {
    fun context(): Context

    fun inject(activity: MainActivity)

    fun inject(fragment: TeamListFragment)

    fun inject(activity: LoginActivity)

    fun inject(service: EsaAccessService)

    fun inject(fragment: PostListFragment)

    fun inject(fragment: MainNavigationFragment)
}
