package net.numa08.gochisou.data.repositories

import io.realm.Realm
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.Team

interface TeamRepository {
    fun findByLoginProfile(realm: Realm, loginProfile: LoginProfile): Team?
}