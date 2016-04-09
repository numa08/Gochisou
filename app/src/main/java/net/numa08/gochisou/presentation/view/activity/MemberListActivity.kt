package net.numa08.gochisou.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_member_list.*
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.presentation.view.fragment.MemberListFragment
import org.jetbrains.anko.support.v4.withArguments
import org.parceler.Parcels

class MemberListActivity : AppCompatActivity() {

    companion object {
        val ARG_LOGIN_PROFILE = "${MemberListActivity::class.simpleName}.ARG_LOGIN_PROFILE"
    }

    val loginProfile by lazy { Parcels.unwrap<LoginProfile>(intent.getParcelableExtra(ARG_LOGIN_PROFILE)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)
        setSupportActionBar(toolbar)
        val fragment = MemberListFragment().withArguments(MemberListFragment.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile))
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }
}
