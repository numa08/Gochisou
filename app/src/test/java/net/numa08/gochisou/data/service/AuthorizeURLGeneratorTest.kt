package net.numa08.gochisou.data.service

import android.net.Uri
import android.os.Build
import net.numa08.gochisou.BuildConfig
import net.numa08.gochisou.testtools.MyTestRunner
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(MyTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
class AuthorizeURLGeneratorTest {

    val generator = AuthorizeURLGenerator()

    @Test
    fun generateURL() {
        val url = Uri.parse(generator.generateAuthorizeURL("clientID", "https://redirect.url"))
        assertThat(url.scheme, `is`("https"))
        assertThat(url.authority, `is`(AuthorizeURLGenerator.ENDPOINT))
        assertThat(url.getQueryParameter("client_id"), `is`("clientID"))
        assertThat(url.getQueryParameter("redirect_uri"), `is`("https://redirect.url"))
        assertThat(url.getQueryParameter("scope"), `is`("write read"))
        assertThat(url.getQueryParameter("response_type"), `is`("code"))
        assert(url.getQueryParameter("state") == null)
    }

}