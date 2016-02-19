package net.numa08.gochisou.data.entity.mapper

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

import java.io.IOException
import java.lang.reflect.Type
import java.util.Date

import javax.inject.Singleton

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmString

@Singleton
class PostEntitiesMapper {

    internal val realmStringToken = object : TypeToken<RealmList<RealmString>>() {}.type

    val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
        override fun shouldSkipField(f: FieldAttributes): Boolean {
            return f.declaringClass == RealmObject::class.java
        }

        override fun shouldSkipClass(clazz: Class<*>): Boolean {
            return false
        }
    }).registerTypeAdapter(realmStringToken, object : TypeAdapter<RealmList<RealmString>>() {
        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: RealmList<RealmString>) {
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): RealmList<RealmString> {
            val list = RealmList<RealmString>()
            `in`.beginArray()
            while (`in`.hasNext()) {
                list.add(RealmString(`in`.nextString()))
            }
            `in`.endArray()
            return list
        }
    }).setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
}
