package net.numa08.gochisou.presentation.internal.di.modules

import dagger.Module
import dagger.Provides
import net.numa08.gochisou.data.entity.mapper.PostEntitiesMapper
import net.numa08.gochisou.data.service.EsaService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Module
class EsaServiceModule {

    @Provides
    @Singleton
    fun providesEsaService(): EsaService {
        val gson = PostEntitiesMapper().gson
        return Retrofit.Builder().baseUrl(END_POINT).addConverterFactory(GsonConverterFactory.create(gson)).build().create(EsaService::class.java)
    }

    companion object {

        private val END_POINT = "https://api.esa.io"
    }

}
