package net.numa08.gochisou.data.repositories

import net.numa08.gochisou.data.model.NavigationIdentifier

abstract class NavigationIdentifierRepository(list: MutableList<NavigationIdentifier>) : MutableList<NavigationIdentifier> by list {
    open fun move(from: Int, to: Int) {
        val i = removeAt(from)
        add(to, i)
    }
}