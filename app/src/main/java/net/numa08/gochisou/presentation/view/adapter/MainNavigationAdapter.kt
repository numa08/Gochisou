package net.numa08.gochisou.presentation.view.adapter

import android.support.design.widget.CustomTabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.presentation.view.fragment.PostDetailFragment
import net.numa08.gochisou.presentation.view.fragment.PostListFragment
import org.jetbrains.anko.support.v4.withArguments
import org.parceler.Parcels

class MainNavigationAdapter(fragmentManager: FragmentManager, val navigationIdentifierRepository: NavigationIdentifierRepository) :
        FragmentStatePagerAdapter(fragmentManager), CustomTabLayout.CustomPagerAdapter {

    override fun customTabView(position: Int, parent: ViewGroup?): View? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.row_main_tab, parent, false) as? TextView
        view?.text = navigationIdentifierRepository[position].name
        return view
    }

    override fun getItem(position: Int): Fragment? =
            navigationIdentifierRepository[position].let {
                when (it) {
                    is NavigationIdentifier.PostNavigationIdentifier -> {
                        PostListFragment().withArguments(
                                PostListFragment.ARG_LOGIN_PROFILE to Parcels.wrap(it.loginProfile))
                    }
                    is NavigationIdentifier.PostDetailNavigationIdentifier -> {
                        PostDetailFragment().withArguments(
                                "loginProfile" to Parcels.wrap(it.loginProfile),
                                "fullName" to it.fullName
                        )
                    }
                }
        }

    override fun getCount(): Int =
            navigationIdentifierRepository.size

}