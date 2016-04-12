package net.numa08.gochisou.data.repositories

import io.realm.Realm
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.Team

class TeamRepositoryImpl : TeamRepository {
    override fun findByLoginProfile(realm: Realm, loginProfile: LoginProfile): Team? =
            realm.where(Team::class.java)
                    .equalTo("loginToken", loginProfile.token)
                    .findFirst()
}