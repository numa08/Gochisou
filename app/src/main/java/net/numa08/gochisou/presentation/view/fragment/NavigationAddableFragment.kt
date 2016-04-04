package net.numa08.gochisou.presentation.view.fragment

import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository

interface NavigationAddable {
    var navigationIdentifierRepository: NavigationIdentifierRepository
    val navigationIdentifier: NavigationIdentifier
    fun onAddClicked() {
        navigationIdentifierRepository.add(navigationIdentifier)
    }

    fun shouldShowAddButton() = !navigationIdentifierRepository.contains(navigationIdentifier)
}