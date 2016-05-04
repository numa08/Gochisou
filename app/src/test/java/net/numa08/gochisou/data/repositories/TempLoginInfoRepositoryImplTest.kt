package net.numa08.gochisou.data.repositories

import android.os.Build
import net.numa08.gochisou.BuildConfig
import net.numa08.gochisou.data.model.Client
import net.numa08.gochisou.data.model.TempLoginInfo
import net.numa08.gochisou.testtools.RoboSharedPreferencesRule
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, manifest = Config.NONE, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class TempLoginInfoRepositoryImplTest {

    @get:Rule
    val preferenceRule = RoboSharedPreferencesRule()

    lateinit var repository: TempLoginInfoRepository

    @Before
    fun initRepository() {
        repository = TempLoginInfoRepositoryImpl(preferenceRule.sharedPreferences)
    }

    @Test
    fun initEmpty() {
        assertThat(repository.isEmpty(), `is`(true))
    }

    @Test
    fun putTempLoginInfo() {
        repository["key"] = TempLoginInfo(
                teamName = "teamName",
                client = Client(id = "id", secret = "secret"),
                redirectURL = "https://redirect.url"
        )
        val saved = repository["key"]

        val newRepository = TempLoginInfoRepositoryImpl(preferenceRule.sharedPreferences)
        assertThat(newRepository["key"], `is`(saved))
    }

    @Test
    fun clearTempLoginInfo() {
        repository["key"] = TempLoginInfo(
                teamName = "teamName",
                client = Client(id = "id", secret = "secret"),
                redirectURL = "https://redirect.url"
        )
        repository.clear()
        val newRepository = TempLoginInfoRepositoryImpl(preferenceRule.sharedPreferences)
        assertThat(newRepository.isEmpty(), `is`(true))
    }

    @Test
    fun removeTempLoginInfo() {
        repository["key"] = TempLoginInfo(
                teamName = "teamName",
                client = Client(id = "id", secret = "secret"),
                redirectURL = "https://redirect.url"
        )
        repository.remove("key")

        val newRepository = TempLoginInfoRepositoryImpl(preferenceRule.sharedPreferences)
        assert(newRepository["key"] == null)
    }
}