package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main_navigaiton.*
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.presentation.view.adapter.MainNavigationAdapter
import javax.inject.Inject

class MainNavigationFragment : Fragment() {

    lateinit var navigationIdentifierRepository: NavigationIdentifierRepository
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_main_navigaiton, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainNavigationAdapter(childFragmentManager, navigationIdentifierRepository)
        content_pager.adapter = adapter
        (activity as? TabLayoutActivity)?.tabLayout?.setupWithViewPager(content_pager)
    }

    interface TabLayoutActivity {
        val tabLayout: TabLayout
    }

}