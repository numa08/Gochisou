package net.numa08.gochisou.data.repositories

import android.os.Build
import net.numa08.gochisou.BuildConfig
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.testtool.rules.RoboSharedPreferencesRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, manifest = Config.NONE, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class LoginProfileRepositoryImplTest {

    @get:Rule
    val preferenceRule = RoboSharedPreferencesRule()

    lateinit var repository: LoginProfileRepositoryImpl

    @Before
    fun initRepository() {
        repository = LoginProfileRepositoryImpl(preferenceRule.sharedPreferences)
    }

    @Test
    fun defaultRepository() {
        assert(repository.isEmpty())
    }

    @Test
    fun addRepository() {
        val profile = LoginProfile("team", "token")
        repository.add(profile)
        assert(repository[0] == profile)
    }

    @Test
    fun restoreFromSharedPreferences() {
        val profile = LoginProfile("team", "token")
        repository.add(profile)
        val newRepository = LoginProfileRepositoryImpl(repository.sharedPreferences)
        assert(newRepository[0] == profile)
    }

    @Test
    fun findLoginProfileI() {
        val profile = LoginProfile("team", "token")
        repository.add(profile)
        val found = repository.find("token")
        assert(found!! == profile)
    }
}