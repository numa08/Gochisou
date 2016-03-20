package net.numa08.gochisou.data.repositories

import net.numa08.gochisou.data.model.LoginProfile

abstract class LoginProfileRepository(list: MutableList<LoginProfile>) : MutableList<LoginProfile> by list {}