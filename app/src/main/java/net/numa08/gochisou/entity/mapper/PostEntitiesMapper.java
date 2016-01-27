package net.numa08.gochisou.entity.mapper;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import javax.inject.Singleton;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmString;

@Singleton
public class PostEntitiesMapper {

    final Type realmStringToken = new TypeToken<RealmList<RealmString>>(){}.getType();
    final Type dateToken = new TypeToken<Date>(){}.getType();

    final Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }            })
            .registerTypeAdapter(realmStringToken, new TypeAdapter<RealmList<RealmString>>() {
                @Override
                public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {
                }

                @Override
                public RealmList<RealmString> read(JsonReader in) throws IOException {
                    final RealmList<RealmString> list = new RealmList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(new RealmString(in.nextString()));
                    }
                    in.endArray();
                    return list;
                }
            })
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public Gson getGson() {
        return gson;
    }
}
