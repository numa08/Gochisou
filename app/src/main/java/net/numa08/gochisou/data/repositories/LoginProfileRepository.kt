package net.numa08.gochisou.data.repositories

import android.databinding.ObservableList
import net.numa08.gochisou.data.model.LoginProfile

abstract class LoginProfileRepository(val list: ObservableList<LoginProfile>) : ObservableList<LoginProfile> by list {

    fun find(token: String): LoginProfile? = list.find { it.token.accessToken == token }

    fun updateOrSet(element: LoginProfile) {
        val idx = indexOf(find(element.token.accessToken))
        val f = when(idx) {
            -1 -> {i: Int,e: LoginProfile -> add(e) }
            else -> {i: Int, e: LoginProfile -> set(i,e)}
        }
        f(idx, element)
    }
}