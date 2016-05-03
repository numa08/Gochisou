package net.numa08.gochisou.data.entity.mapper

import net.numa08.gochisou.data.model.Client
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.model.Token
import org.junit.Test

class NavigationIdentifierMapperTest {

    @Test
    fun serializeNavigationIdentifier() {
        val nav = NavigationIdentifier.PostNavigationIdentifier("name", "avatar", LoginProfile("teamURL", Client("id", "secret"), Token("accessToken", "tokenType", "scope", 0L)))
        val json = NavigationIdentifierMapper().gson.toJson(nav, NavigationIdentifier::class.java)
        val deserialize = NavigationIdentifierMapper().gson.fromJson(json, NavigationIdentifier::class.java)

        assert(deserialize is NavigationIdentifier.PostNavigationIdentifier)
        assert(deserialize.name == "name")
        assert(deserialize.avatar == "avatar")
        assert(deserialize.loginProfile.teamURL == "teamURL")
        assert(deserialize.loginProfile.client == Client("id", "secret"))
        assert(deserialize.loginProfile.token == Token("accessToken", "tokenType", "scope", 0L))
    }

    @Test
    fun serializePostDetail() {
        val nav = NavigationIdentifier.PostDetailNavigationIdentifier("name", "avatar", LoginProfile("teamURL", Client("id", "secret"), Token("accessToken", "tokenType", "scope", 0L)), "fullName")
        val json = NavigationIdentifierMapper().gson.toJson(nav, NavigationIdentifier::class.java)
        val deserialize = NavigationIdentifierMapper().gson.fromJson(json, NavigationIdentifier::class.java)

        assert(deserialize is NavigationIdentifier.PostDetailNavigationIdentifier)
        assert(deserialize.name == "name")
        assert(deserialize.avatar == "avatar")
        assert(deserialize.loginProfile.teamURL == "teamURL")
        assert(deserialize.loginProfile.client == Client("id", "secret"))
        assert(deserialize.loginProfile.token == Token("accessToken", "tokenType", "scope", 0L))
        assert((deserialize as NavigationIdentifier.PostDetailNavigationIdentifier).fullName == "fullName")
    }


}