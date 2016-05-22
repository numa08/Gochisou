package net.numa08.gochisou.presentation.view.fragment

import android.content.Context
import android.os.Build
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.internal.RealmCore
import net.numa08.gochisou.BuildConfig
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.data.model.*
import net.numa08.gochisou.presentation.internal.di.components.DaggerApplicationComponent
import net.numa08.gochisou.presentation.internal.di.modules.ApplicationModule
import net.numa08.gochisou.presentation.internal.di.modules.LoginProfileRepositoryModule
import net.numa08.gochisou.presentation.internal.di.modules.NavigationIdentifierRepositoryModule
import net.numa08.gochisou.testtools.MyTestRunner
import net.numa08.gochisou.testtools.RoboSharedPreferencesRule
import org.hamcrest.CoreMatchers.`is`
import org.jetbrains.anko.support.v4.withArguments
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.parceler.Parcels
import org.powermock.api.mockito.PowerMockito.*
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.rule.PowerMockRule
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil

@RunWith(MyTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
@PrepareForTest(Realm::class, RealmConfiguration::class, RealmResults::class, RealmCore::class)
class PostListFragmentTest {

    @Suppress("unused")
    @get:Rule
    val mockRule = PowerMockRule()
    @get:Rule
    val preferencesRule = RoboSharedPreferencesRule()

    @Before
    fun mockRealm() {
        mockStatic(Realm::class.java)
        mockStatic(RealmConfiguration::class.java)
        mockStatic(RealmCore::class.java)
        val realm = mock(Realm::class.java)
        val configuration = mock(RealmConfiguration::class.java)
        doNothing().`when`(RealmCore::class.java)
        RealmCore.loadLibrary(any(Context::class.java))
        whenNew(RealmConfiguration::class.java).withAnyArguments().thenReturn(configuration)
        `when`(Realm.getInstance(any(RealmConfiguration::class.java))).thenReturn(realm)
    }

    @Before
    fun initModules() {
        GochisouApplication().onCreate()
        val app = GochisouApplication.application!!
        app.applicationComponent =
                DaggerApplicationComponent
                        .builder()
                        .applicationModule(ApplicationModule(RuntimeEnvironment.application))
                        .navigationIdentifierRepositoryModule(NavigationIdentifierRepositoryModule(preferencesRule.sharedPreferences))
                        .loginProfileRepositoryModule(LoginProfileRepositoryModule(preferencesRule.sharedPreferences))
                        .build()
    }

    @Test
    fun should_show_add_navigation_identifier_button() {
        val loginProfile = LoginProfile(Client("id", "secret"), Token("accessToken", "tokenType", "scope", 0L), Team("team", "privacy", "description", "icon", "url"))
        val fragment = PostListFragment().withArguments(ArgLoginProfile.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile))
        SupportFragmentTestUtil.startVisibleFragment(fragment)

        assert(fragment.shouldShowAddButton() == true)
        assert(fragment.hasOptionsMenu() == true)
    }

    @Test
    fun get_navigation_identifier() {
        val loginProfile = LoginProfile(Client("id", "secret"), Token("accessToken", "tokenType", "scope", 0L), Team("team", "privacy", "description", "icon", "url"))
        val fragment = PostListFragment().withArguments(ArgLoginProfile.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile))
        SupportFragmentTestUtil.startVisibleFragment(fragment)

        val navigationIdentifier = fragment.navigationIdentifier
        val expected : NavigationIdentifier = NavigationIdentifier.PostNavigationIdentifier(
                name = "team",
                avatar = "icon",
                loginProfile = loginProfile
        )
        assertThat(navigationIdentifier, `is`(expected))
    }

    @Test
    fun should_not_show_navigation_identifier_button() {
        val loginProfile = LoginProfile(Client("id", "secret"), Token("accessToken", "tokenType", "scope", 0L), Team("team", "privacy", "description", "icon", "url"))
        val repository = GochisouApplication.application?.applicationComponent?.navigationIdentifierRepository()
        repository?.add(NavigationIdentifier.PostNavigationIdentifier(
                name = "team",
                avatar = "icon",
                loginProfile = loginProfile
        ))
        val fragment = PostListFragment().withArguments(ArgLoginProfile.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile))
        SupportFragmentTestUtil.startVisibleFragment(fragment)

        assertThat(fragment.shouldShowAddButton(), `is`(false))
        assertThat(fragment.hasOptionsMenu(), `is`(false))
    }
}