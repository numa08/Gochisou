package net.numa08.gochisou.presentation.internal.di.modules

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.data.repositories.LoginProfileRepositoryImpl
import net.numa08.gochisou.data.repositories.NavigaitonIdentifierRepositoryImpl
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository

@Module
class LoginProfileRepositoryModule {

    @Provides
    fun providesLoginProfileRepository(sharedPreferences: SharedPreferences) : LoginProfileRepository
        = LoginProfileRepositoryImpl(sharedPreferences)

}

@Module
class NavigationIdentifierRepositoryModule {
    @Provides
    fun providesNavigationIdentifierRepository(sharedPreferences: SharedPreferences): NavigationIdentifierRepository
            = NavigaitonIdentifierRepositoryImpl(sharedPreferences)
}