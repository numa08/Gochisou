package net.numa08.gochisou.data.repositories

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import net.numa08.gochisou.data.model.NavigationIdentifier
import java.util.*

class NavigaitonIdentifierRepositoryImpl(val sharedPreferences: SharedPreferences) : NavigationIdentifierRepository(
        GsonBuilder().create().fromJson(sharedPreferences.getString(PREFERENCE_KEY, "[]"), object : TypeToken<ArrayList<NavigationIdentifier>>() {}.type)
) {
    companion object {
        val PREFERENCE_KEY = "${NavigaitonIdentifierRepositoryImpl::class.simpleName}.PREFERENCE_KEY"
    }

    val gson = GsonBuilder().create()

    override fun add(element: NavigationIdentifier): Boolean = super.add(element).let {
        write()
        it
    }

    override fun addAll(elements: Collection<NavigationIdentifier>): Boolean = super.addAll(elements).let {
        write()
        it
    }

    override fun addAll(index: Int, elements: Collection<NavigationIdentifier>): Boolean = super.addAll(index, elements).let {
        write()
        it
    }

    override fun clear() {
        super.clear()
        write()
    }

    private fun write() {
        sharedPreferences.edit()
                .putString(PREFERENCE_KEY, gson.toJson(NavigationIdentifierList(this), NavigationIdentifierList::class.java))
                .apply()
    }
}

private class NavigationIdentifierList(collection: Collection<NavigationIdentifier> = emptyList()) : ArrayList<NavigationIdentifier>(collection)