package net.numa08.gochisou.data.repositories

import android.content.SharedPreferences

interface SharedPreferencesRepository {

    val sharedPreferences: SharedPreferences
    val preferenceKey: String

    fun toJson(): String

    fun write() {
        sharedPreferences
                .edit()
                .putString(preferenceKey, toJson())
                .apply()
    }
}