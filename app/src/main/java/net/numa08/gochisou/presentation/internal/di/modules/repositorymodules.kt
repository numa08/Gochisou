package net.numa08.gochisou.presentation.internal.di.modules

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import net.numa08.gochisou.data.repositories.*
import javax.inject.Singleton

@Module
@Singleton
class LoginProfileRepositoryModule(val sharedPreferences: SharedPreferences) {

    @Provides
    @Singleton
    fun providesLoginProfileRepository(): LoginProfileRepository
        = LoginProfileRepositoryImpl(sharedPreferences)

}

@Module
@Singleton
class NavigationIdentifierRepositoryModule(val sharedPreferences: SharedPreferences) {

    @Provides
    @Singleton
    fun providesNavigationIdentifierRepository(): NavigationIdentifierRepository
            = NavigationIdentifierRepositoryImpl(sharedPreferences)
}

@Module
@Singleton
open class TeamRepositoryModule {

    @Provides
    @Singleton
    open fun providesTeamRepository(): TeamRepository = TeamRepositoryImpl()

}

@Module
@Singleton
class TempLoginInfoRepositoryModule {

    var singleTon: TempLoginInfoRepository? = null

    @Provides
    @Singleton
    fun providesTempLoginInfoRepository(sharedPreferences: SharedPreferences): TempLoginInfoRepository
            = singleTon ?: TempLoginInfoRepositoryImpl(sharedPreferences).apply { singleTon = this }

}