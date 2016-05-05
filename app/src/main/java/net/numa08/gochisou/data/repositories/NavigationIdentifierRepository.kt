package net.numa08.gochisou.data.repositories

import android.databinding.ObservableList
import net.numa08.gochisou.data.model.NavigationIdentifier

abstract class NavigationIdentifierRepository(list: ObservableList<NavigationIdentifier>) : ObservableList<NavigationIdentifier> by list {
    open fun move(from: Int, to: Int) {
        val i = removeAt(from)
        add(to, i)
    }
}