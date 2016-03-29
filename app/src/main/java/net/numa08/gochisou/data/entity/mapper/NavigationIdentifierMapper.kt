package net.numa08.gochisou.data.entity.mapper

import com.google.gson.*
import net.numa08.gochisou.data.model.NavigationIdentifier
import java.lang.reflect.Type
import javax.inject.Singleton

@Singleton
class NavigationIdentifierMapper {

    companion object {
        internal val CLASS = "class"
        internal val INSTANCE = "instance"
    }

    val gson = GsonBuilder()
            .registerTypeAdapter(NavigationIdentifier::class.java, object : JsonSerializer<NavigationIdentifier>, JsonDeserializer<NavigationIdentifier> {
                override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): NavigationIdentifier? = json?.let {
                    val obj = it.asJsonObject
                    val className = obj.getAsJsonPrimitive(CLASS)?.asString
                    val klass = Class.forName(className)
                    context?.deserialize(obj.get(INSTANCE), klass)
                }

                override fun serialize(src: NavigationIdentifier?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? = src?.let {
                    val json = JsonObject()
                    val className = it.javaClass.name
                    json.addProperty(CLASS, className)
                    val elem = context?.serialize(src)
                    json.add(INSTANCE, elem)
                    json
                }
            })
            .create()


}