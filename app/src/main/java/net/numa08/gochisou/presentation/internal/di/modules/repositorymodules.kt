package net.numa08.gochisou.presentation.internal.di.modules

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.data.repositories.LoginProfileRepositoryImpl
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepositoryImpl
import javax.inject.Singleton

@Module
@Singleton
class LoginProfileRepositoryModule {

    @Provides
    @Singleton
    fun providesLoginProfileRepository(sharedPreferences: SharedPreferences) : LoginProfileRepository
        = LoginProfileRepositoryImpl(sharedPreferences)

}

@Module
@Singleton
class NavigationIdentifierRepositoryModule {

    @Provides
    @Singleton
    fun providesNavigationIdentifierRepository(sharedPreferences: SharedPreferences): NavigationIdentifierRepository
            = NavigationIdentifierRepositoryImpl(sharedPreferences)
}