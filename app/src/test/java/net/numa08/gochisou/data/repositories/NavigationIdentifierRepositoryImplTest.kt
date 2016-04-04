package net.numa08.gochisou.data.repositories

import android.os.Build
import net.numa08.gochisou.BuildConfig
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.testtool.rules.RoboSharedPreferencesRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, manifest = Config.NONE, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class NavigationIdentifierRepositoryImplTest {

    @get:Rule
    val preferenceRule = RoboSharedPreferencesRule()

    lateinit var repository: NavigationIdentifierRepositoryImpl

    @Before
    fun initRepository() {
        repository = NavigationIdentifierRepositoryImpl(preferenceRule.sharedPreferences)
    }

    @Test
    fun defaultRepository() {
        assert(repository.isEmpty())
    }

    @Test
    fun addRepository() {
        val identifier = NavigationIdentifier.PostNavigationIdentifier("name", "avatar", LoginProfile("team", "token"))
        repository.add(identifier)
        assert(repository[0] == identifier)
    }

    @Test
    fun restoreFromSharedPreferences() {
        val identifier = NavigationIdentifier.PostNavigationIdentifier("name", "avatar", LoginProfile("team", "token"))
        repository.add(identifier)
        val newRepository = NavigationIdentifierRepositoryImpl(preferenceRule.sharedPreferences)
        assert(newRepository[0] == identifier)
    }

    @Test
    fun moveNavigationIdentifier() {
        val id1 = NavigationIdentifier.PostNavigationIdentifier("id1", "avatar", LoginProfile("team", "token"))
        val id2 = NavigationIdentifier.PostNavigationIdentifier("id2", "avatar", LoginProfile("team", "token"))
        val id3 = NavigationIdentifier.PostNavigationIdentifier("id3", "avatar", LoginProfile("team", "token"))
        val id4 = NavigationIdentifier.PostNavigationIdentifier("id4", "avatar", LoginProfile("team", "token"))
        val id5 = NavigationIdentifier.PostNavigationIdentifier("id5", "avatar", LoginProfile("team", "token"))
        repository.addAll(listOf(id1, id2, id3, id4, id5))
        repository.move(1, 3)
        assert(repository.toList() == listOf(id1, id3, id4, id2, id5))
    }
}