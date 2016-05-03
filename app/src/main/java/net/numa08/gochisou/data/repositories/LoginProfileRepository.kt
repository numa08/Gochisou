package net.numa08.gochisou.data.repositories

import net.numa08.gochisou.data.model.LoginProfile

abstract class LoginProfileRepository(val list: MutableList<LoginProfile>) : MutableList<LoginProfile> by list {

    fun find(token: String): LoginProfile? = list.find { it.token.accessToken == token }

}