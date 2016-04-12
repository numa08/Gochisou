package net.numa08.gochisou.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_member_list.*
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.Member
import net.numa08.gochisou.presentation.view.fragment.ArgLoginProfile
import net.numa08.gochisou.presentation.view.fragment.MemberListFragment
import net.numa08.gochisou.presentation.view.fragment.MembersPostListFragment
import org.jetbrains.anko.support.v4.withArguments
import org.parceler.Parcels

class MemberListActivity : AppCompatActivity(), IntentLoginProfile, MemberListFragment.Callback {

    companion object {
        val BACK_STACK = "${MemberListActivity::class.simpleName}.BACK_STACK"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)
        setSupportActionBar(toolbar)
        val fragment = MemberListFragment().withArguments(MemberListFragment.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile()))
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(BACK_STACK)
                .commit()
    }

    override fun onClickMember(member: Member?) {
        val screenName = member?.screenName
        if (screenName != null) {
            val fragment = MembersPostListFragment().withArguments(
                    MembersPostListFragment.ARG_SCREEN_NAME to screenName,
                    ArgLoginProfile.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile()))
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .addToBackStack(BACK_STACK)
                    .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFinishAfterTransition()
        }
    }
}
