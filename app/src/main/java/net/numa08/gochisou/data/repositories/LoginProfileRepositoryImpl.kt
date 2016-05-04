package net.numa08.gochisou.data.repositories

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.numa08.gochisou.data.model.LoginProfile
import java.util.*

class LoginProfileRepositoryImpl(override val sharedPreferences: SharedPreferences, val gson: Gson = GsonBuilder().create())
: LoginProfileRepository(gson.fromJson(sharedPreferences.getString(PREFERENCE_KEY, "[]"), object : TypeToken<ArrayList<LoginProfile>>() {}.type)),
        SharedPreferencesRepository {

    companion object {
        val PREFERENCE_KEY = "${LoginProfileRepositoryImpl::class.simpleName}.PREFERENCE_KEY"
    }

    override val preferenceKey: String
            = PREFERENCE_KEY

    override fun add(element: LoginProfile): Boolean = super.add(element).apply { write() }

    override fun addAll(elements: Collection<LoginProfile>): Boolean = super.addAll(elements).apply { write() }

    override fun addAll(index: Int, elements: Collection<LoginProfile>): Boolean = super.addAll(index, elements).apply { write() }

    override fun clear() {
        super.clear()
        write()
    }

    override fun toJson(): String
            = gson.toJson(this)
}