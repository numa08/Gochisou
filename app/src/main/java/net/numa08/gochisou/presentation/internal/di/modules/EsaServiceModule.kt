package net.numa08.gochisou.presentation.internal.di.modules

import dagger.Module
import dagger.Provides
import net.numa08.gochisou.data.entity.mapper.PostEntitiesMapper
import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.presentation.internal.di.PerActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class EsaServiceModule {

    @Provides
    @PerActivity
    fun providesEsaService(): EsaService {
        val gson = PostEntitiesMapper().gson
        return Retrofit.Builder().baseUrl(END_POINT).addConverterFactory(GsonConverterFactory.create(gson)).build().create(EsaService::class.java)
    }

    companion object {

        private val END_POINT = "https://api.esa.io"
    }

}
