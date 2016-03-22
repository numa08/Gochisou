package net.numa08.gochisou.presentation.view.adapter

import android.support.design.widget.CustomTabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.presentation.view.fragment.PostListFragment
import org.jetbrains.anko.support.v4.withArguments
import org.parceler.Parcels

class MainNavigationAdapter(fragmentManager: FragmentManager, val navigationIdentifierRepository: NavigationIdentifierRepository) :
        FragmentStatePagerAdapter(fragmentManager), CustomTabLayout.CustomPagerAdapter {

    override fun customTabView(position: Int, parent: ViewGroup?): View? {
        val t = TextView(parent?.context)
        t.text = navigationIdentifierRepository[position].name
        return t
    }

    override fun getItem(position: Int): Fragment? =
            navigationIdentifierRepository[position].let {
            PostListFragment().withArguments(
                    PostListFragment.ARG_LOGIN_PROFILE to Parcels.wrap(it.loginProfile)
            )
        }

    override fun getCount(): Int =
            navigationIdentifierRepository.size

}