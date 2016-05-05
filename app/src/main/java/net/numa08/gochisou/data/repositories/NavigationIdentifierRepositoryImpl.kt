package net.numa08.gochisou.data.repositories

import android.content.SharedPreferences
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.numa08.gochisou.data.entity.mapper.NavigationIdentifierMapper
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.NavigationIdentifier
import java.util.*

class NavigationIdentifierRepositoryImpl(override val sharedPreferences: SharedPreferences, val gson: Gson = NavigationIdentifierMapper().gson, val loginProfileRepository: LoginProfileRepository) : NavigationIdentifierRepository(
        gson.fromJson(sharedPreferences.getString(PREFERENCE_KEY, "[]"), object : TypeToken<ArrayList<NavigationIdentifier>>() {}.type))
        , SharedPreferences.OnSharedPreferenceChangeListener
        , SharedPreferencesRepository {
    companion object {
        val PREFERENCE_KEY = "${NavigationIdentifierRepositoryImpl::class.simpleName}.PREFERENCE_KEY"
    }

    val loginProfileRepositoryChangeListener = object : ObservableList.OnListChangedCallback<ObservableArrayList<LoginProfile>>() {

        override fun onItemRangeChanged(repository: ObservableArrayList<LoginProfile>?, p1: Int, p2: Int) {
        }

        override fun onItemRangeMoved(repository: ObservableArrayList<LoginProfile>?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onChanged(repository: ObservableArrayList<LoginProfile>?) {
        }

        override fun onItemRangeRemoved(repository: ObservableArrayList<LoginProfile>?, p1: Int, p2: Int) {
        }

        override fun onItemRangeInserted(repository: ObservableArrayList<LoginProfile>?, p1: Int, p2: Int) {
            for (i in 0 until p2) {
                val loginProfile = repository!![p1 + i]
                this@NavigationIdentifierRepositoryImpl.add(
                        NavigationIdentifier.PostNavigationIdentifier(
                                name = loginProfile.teamName,
                                avatar = "",
                                loginProfile = loginProfile
                        )
                )
            }
        }

    }


    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        loginProfileRepository.addOnListChangedCallback(loginProfileRepositoryChangeListener)
    }

    @Suppress("unused")
    fun finalize() {
        loginProfileRepository.removeOnListChangedCallback(loginProfileRepositoryChangeListener)
    }

    override fun add(element: NavigationIdentifier): Boolean = super.add(element).apply { write() }

    override fun addAll(elements: Collection<NavigationIdentifier>): Boolean = super.addAll(elements).apply { write() }

    override fun addAll(index: Int, elements: Collection<NavigationIdentifier>): Boolean = super.addAll(index, elements).apply { write() }

    override fun removeAt(index: Int): NavigationIdentifier = super.removeAt(index).apply { write() }

    override fun set(index: Int, element: NavigationIdentifier): NavigationIdentifier = super.set(index, element).apply { write() }

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

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key == PREFERENCE_KEY) {
            sync()
        }
    }

    override val preferenceKey: String
            = PREFERENCE_KEY

    override fun toJson(): String
            = gson.toJson(this)
}