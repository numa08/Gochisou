package net.numa08.gochisou.data.repositories

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import net.numa08.gochisou.data.entity.mapper.NavigationIdentifierMapper
import net.numa08.gochisou.data.model.NavigationIdentifier
import java.util.*

class NavigationIdentifierRepositoryImpl(val sharedPreferences: SharedPreferences) : NavigationIdentifierRepository(
        NavigationIdentifierMapper().gson.fromJson(sharedPreferences.getString(PREFERENCE_KEY, "[]"), object : TypeToken<ArrayList<NavigationIdentifier>>() {}.type)
), SharedPreferences.OnSharedPreferenceChangeListener {
    companion object {
        val PREFERENCE_KEY = "${NavigationIdentifierRepositoryImpl::class.simpleName}.PREFERENCE_KEY"
    }

    val gson = NavigationIdentifierMapper().gson

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

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

    override fun removeAt(index: Int): NavigationIdentifier = super.removeAt(index).let { write();it }

    override fun set(index: Int, element: NavigationIdentifier): NavigationIdentifier = super.set(index, element).let { write();it }

    override fun move(from: Int, to: Int) {
        val i = super.removeAt(from)
        super.add(to, i)
        write()
    }

    override fun clear() {
        super.clear()
        write()
    }

    private fun sync() {
        val list = gson.fromJson<MutableList<NavigationIdentifier>>(sharedPreferences.getString(PREFERENCE_KEY, "[]"), object : TypeToken<ArrayList<NavigationIdentifier>>() {}.type)
        repeat(list.size, {
            if (it < super.size) {
                super.set(it, list[it])
            } else {
                super.add(list[it])
            }
        })
    }

    private fun write() {
        sharedPreferences.edit()
                .putString(PREFERENCE_KEY, gson.toJson(this))
                .apply()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key == PREFERENCE_KEY) {
            sync()
        }
    }
}