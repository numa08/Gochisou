package net.numa08.gochisou.presentation.internal.di.modules

import dagger.Module
import dagger.Provides
import net.numa08.gochisou.data.service.AuthorizeURLGenerator
import javax.inject.Singleton

@Module
@Singleton
class AuthorizeURLGeneratorModule {

    companion object {
        val singleTon by lazy { AuthorizeURLGenerator() }
    }

    @Provides
    @Singleton
    fun providesAuthorizeURLGenerator(): AuthorizeURLGenerator = singleTon
}