package net.numa08.gochisou.presentation.internal.di.modules;

import com.google.gson.Gson;

import net.numa08.gochisou.data.entity.mapper.PostEntitiesMapper;
import net.numa08.gochisou.data.service.EsaService;
import net.numa08.gochisou.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

@Module
public class EsaServiceModule {

    private static final String END_POINT = "https://api.esa.io";

    @Provides
    @PerActivity
    public EsaService providesEsaService() {
        final Gson gson = new PostEntitiesMapper().getGson();
        return new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(EsaService.class);
    }

}
