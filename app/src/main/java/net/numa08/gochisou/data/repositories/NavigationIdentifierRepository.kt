package net.numa08.gochisou.data.repositories

import net.numa08.gochisou.data.model.NavigationIdentifier

abstract class NavigationIdentifierRepository(list: MutableList<NavigationIdentifier>) : MutableList<NavigationIdentifier> by list