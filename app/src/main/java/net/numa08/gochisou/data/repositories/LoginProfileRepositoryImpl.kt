package net.numa08.gochisou.data.repositories

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.numa08.gochisou.data.model.LoginProfile
import java.util.*

class LoginProfileRepositoryImpl  constructor(val sharedPreferences: SharedPreferences) : LoginProfileRepository(
   GsonBuilder().create().fromJson(sharedPreferences.getString(PREFERENCE_KEY, "[]"), object : TypeToken<ArrayList<LoginProfile>>(){}.type)
) {

    companion object {
        val PREFERENCE_KEY = "${LoginProfileRepositoryImpl::class.simpleName}.PREFERENCE_KEY"
    }

    val gson = GsonBuilder().create()

    override fun add(element: LoginProfile): Boolean = super.add(element).let {
        write()
        it
    }

    override fun addAll(elements: Collection<LoginProfile>): Boolean = super.addAll(elements).let {
        write()
        it
    }

    override fun addAll(index: Int, elements: Collection<LoginProfile>): Boolean = super.addAll(index, elements).let {
        write()
        it
    }

    override fun clear() {
        super.clear()
        write()
    }

    private fun write() {
        sharedPreferences.edit()
                .putString(PREFERENCE_KEY, gson.toJson(LoginProfileList(this), LoginProfileList::class.java))
                .apply()
    }

}

private class LoginProfileList(collection: Collection<LoginProfile> = listOf()) : ArrayList<LoginProfile>(collection) {}