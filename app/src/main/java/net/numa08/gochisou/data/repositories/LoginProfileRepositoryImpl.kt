package net.numa08.gochisou.data.repositories

import android.content.SharedPreferences
import android.databinding.ObservableArrayList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.numa08.gochisou.data.model.LoginProfile

class LoginProfileRepositoryImpl(override val sharedPreferences: SharedPreferences, val gson: Gson = GsonBuilder().create())
: LoginProfileRepository(gson.fromJson<ObservableArrayList<LoginProfile>>(sharedPreferences.getString(PREFERENCE_KEY, "[]"), object : TypeToken<ObservableArrayList<LoginProfile>>() {}.type))
        , SharedPreferencesRepository {

    companion object {
        val PREFERENCE_KEY = "${LoginProfileRepositoryImpl::class.simpleName}.PREFERENCE_KEY"
    }

    override val preferenceKey: String
            = PREFERENCE_KEY

    override fun add(element: LoginProfile): Boolean = super.add(element).apply { write() }

    override fun addAll(elements: Collection<LoginProfile>): Boolean = super.addAll(elements).apply { write() }

    override fun addAll(index: Int, elements: Collection<LoginProfile>): Boolean = super.addAll(index, elements).apply { write() }

    override fun set(index: Int, element: LoginProfile?): LoginProfile? {
        return super.set(index, element).apply { write() }
    }

    override fun clear() {
        super.clear()
        write()
    }

    override fun toJson(): String
            = gson.toJson(this)
}