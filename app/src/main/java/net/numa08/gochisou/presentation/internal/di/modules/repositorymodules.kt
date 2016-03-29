package net.numa08.gochisou.presentation.internal.di.modules

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import net.numa08.gochisou.data.entity.mapper.NavigationIdentifierMapper
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.data.repositories.LoginProfileRepositoryImpl
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepositoryImpl

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
            = NavigationIdentifierRepositoryImpl(sharedPreferences, NavigationIdentifierMapper().gson)
}