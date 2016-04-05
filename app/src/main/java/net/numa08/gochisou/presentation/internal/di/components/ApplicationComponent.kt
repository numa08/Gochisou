package net.numa08.gochisou.presentation.internal.di.components

import android.content.Context
import dagger.Component
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.presentation.internal.di.modules.*
import net.numa08.gochisou.presentation.service.EsaAccessService
import net.numa08.gochisou.presentation.view.activity.LoginActivity
import net.numa08.gochisou.presentation.view.activity.MainActivity
import net.numa08.gochisou.presentation.view.activity.PostDetailActivity
import net.numa08.gochisou.presentation.view.activity.PostListActivity
import net.numa08.gochisou.presentation.view.fragment.*
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, EsaServiceModule::class, RealmConfigurationModule::class, LoginProfileRepositoryModule::class, NavigationIdentifierRepositoryModule::class))
interface ApplicationComponent {
    fun context(): Context

    fun navigationIdentifierRepository(): NavigationIdentifierRepository

    fun inject(activity: MainActivity)

    fun inject(fragment: TeamListFragment)

    fun inject(activity: LoginActivity)

    fun inject(service: EsaAccessService)

    fun inject(fragment: PostListFragment)

    fun inject(fragment: MainNavigationFragment)

    fun inject(activity: PostDetailActivity)

    fun inject(fragment: PostDetailFragment)

    fun inject(fragment: EditNavigationIdentifierFragment)

    fun inject(activity: PostListActivity)

    fun activityComponent(): ActivityComponent
}
