package net.numa08.gochisou.data.repositories

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.numa08.gochisou.data.model.TempLoginInfo
import java.util.*

class TempLoginInfoRepositoryImpl(override val sharedPreferences: SharedPreferences, val gson: Gson = GsonBuilder().create())
: TempLoginInfoRepository(gson.fromJson<HashMap<String, TempLoginInfo>>(sharedPreferences.getString(PREFERENCE_KEY, "{}"), object : TypeToken<HashMap<String, TempLoginInfo>>() {}.type)),
        SharedPreferencesRepository {
    companion object {
        val PREFERENCE_KEY = "${TempLoginInfoRepositoryImpl::class.java.simpleName}.PREFERENCE_KEY"
    }

    override val preferenceKey: String
        get() = PREFERENCE_KEY

    override fun clear() {
        super.clear()
        write()
    }

    override fun put(key: String, value: TempLoginInfo): TempLoginInfo?
            = super.put(key, value).apply { write() }

    override fun putAll(from: Map<out String, TempLoginInfo>)
            = super.putAll(from).apply { write() }

    override fun remove(key: String): TempLoginInfo?
            = super.remove(key).apply { write() }

    override fun toJson(): String
            = gson.toJson(this)
}