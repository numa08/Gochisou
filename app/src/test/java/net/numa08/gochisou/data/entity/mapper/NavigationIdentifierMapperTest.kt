package net.numa08.gochisou.data.entity.mapper

import net.numa08.gochisou.data.model.*
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class NavigationIdentifierMapperTest {

    @Test
    fun serializeNavigationIdentifier() {
        val nav = NavigationIdentifier.PostNavigationIdentifier("name", "avatar", LoginProfile(Client("id", "secret"), Token("accessToken", "tokenType", "scope", 0L), Team("team", "privacy", "description", "icon", "url")))
        val json = NavigationIdentifierMapper().gson.toJson(nav, NavigationIdentifier::class.java)
        val deserialize = NavigationIdentifierMapper().gson.fromJson(json, NavigationIdentifier::class.java)

        assert(deserialize is NavigationIdentifier.PostNavigationIdentifier)
        assert(deserialize.name == "name")
        assert(deserialize.avatar == "avatar")
        assert(deserialize.loginProfile.team.name == "team")
        assert(deserialize.loginProfile.client == Client("id", "secret"))
        assert(deserialize.loginProfile.token == Token("accessToken", "tokenType", "scope", 0L))
    }

    @Test
    fun serializePostDetail() {
        val nav = NavigationIdentifier.PostDetailNavigationIdentifier("name", "avatar", LoginProfile(Client("id", "secret"), Token("accessToken", "tokenType", "scope", 0L), Team("team", "privacy", "description", "icon", "url")), "fullName")
        val json = NavigationIdentifierMapper().gson.toJson(nav, NavigationIdentifier::class.java)
        val deserialize = NavigationIdentifierMapper().gson.fromJson(json, NavigationIdentifier::class.java)

        assertThat(deserialize.name, `is`("name"))
        assertThat(deserialize.avatar, `is`("avatar"))
        assertThat(deserialize.loginProfile.team.name, `is`("team"))
        assertThat(deserialize.loginProfile.client, `is`(Client("id", "secret")))
        assertThat(deserialize.loginProfile.token, `is`(Token("accessToken", "tokenType", "scope", 0L)))
        assertThat((deserialize as NavigationIdentifier.PostDetailNavigationIdentifier).fullName, `is`("fullName"))
    }


}