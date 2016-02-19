package net.numa08.gochisou.data.entity.mapper

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmString
import org.apache.commons.lang3.time.DateFormatUtils
import org.apache.commons.lang3.time.DateUtils
import java.io.IOException
import java.util.*
import javax.inject.Singleton

@Singleton
class PostEntitiesMapper {

    internal val realmStringToken = object : TypeToken<RealmList<RealmString>>() {}.type
    internal val dateToken = object : TypeToken<Date>() {}.type

    val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
        override fun shouldSkipField(f: FieldAttributes): Boolean {
            return f.declaringClass == RealmObject::class.java
        }

        override fun shouldSkipClass(clazz: Class<*>): Boolean {
            return false
        }
    }).registerTypeAdapter(realmStringToken, object : TypeAdapter<RealmList<RealmString>>() {
        override fun write(p0: JsonWriter?, p1: RealmList<RealmString>?) {}

        @Throws(IOException::class)
        override fun read(`in`: JsonReader?): RealmList<RealmString>? = `in`?.let { i ->
            val list = RealmList<RealmString>()
            i.beginArray()
            while(i.hasNext()) {
                list.add(RealmString(i.nextString()))
            }
            i.endArray()
            list
        }
    }).registerTypeAdapter(dateToken, object : TypeAdapter<Date>() {
        override fun write(p0: JsonWriter?, p1: Date?) {}

        override fun read(`in`: JsonReader?): Date? = `in`?.let { i ->
            val pattern = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.pattern
            DateUtils.parseDate(i.nextString(), pattern)
        }
    }).create()
}
