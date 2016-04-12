package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.view.MenuItem
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import org.parceler.Parcels

interface ArgLoginProfile {
    companion object {
        val ARG_LOGIN_PROFILE = "${ArgLoginProfile::class.qualifiedName}.ARG_LOGIN_PROFILE"
    }

    fun getArguments(): Bundle?
    fun loginProfile(): LoginProfile
            = Parcels.unwrap<LoginProfile>(getArguments()!!.getParcelable(ARG_LOGIN_PROFILE))
}

interface NavigationAddable {
    val navigationIdentifierRepository: NavigationIdentifierRepository
    val navigationIdentifier: NavigationIdentifier
    fun onAddClicked() {
        navigationIdentifierRepository.add(navigationIdentifier)
    }

    fun shouldShowAddButton() = !navigationIdentifierRepository.contains(navigationIdentifier)

    fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.add_navigation_identifier) {
            onAddClicked()
            return true
        }
        return false
    }
}