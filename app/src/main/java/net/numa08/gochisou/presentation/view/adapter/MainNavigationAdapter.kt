package net.numa08.gochisou.presentation.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.presentation.view.fragment.PostListFragment
import org.jetbrains.anko.support.v4.withArguments
import org.parceler.Parcels

class MainNavigationAdapter(fragmentManager: FragmentManager, val loginProfileRepository: LoginProfileRepository) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? =
        loginProfileRepository[position].let {
            PostListFragment().withArguments(
                    PostListFragment.ARG_LOGIN_PROFILE to Parcels.wrap(it)
            )
        }

    override fun getCount(): Int =
        loginProfileRepository.size

}