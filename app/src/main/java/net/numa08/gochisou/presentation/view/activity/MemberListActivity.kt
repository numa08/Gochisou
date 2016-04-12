package net.numa08.gochisou.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_member_list.*
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.Member
import net.numa08.gochisou.data.model.Post
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.presentation.service.EsaAccessService
import net.numa08.gochisou.presentation.view.fragment.MemberListFragment
import net.numa08.gochisou.presentation.view.fragment.PostListFragment
import org.jetbrains.anko.support.v4.withArguments
import org.parceler.Parcels

class MemberListActivity : AppCompatActivity(), MemberListFragment.Callback {

    companion object {
        val ARG_LOGIN_PROFILE = "${MemberListActivity::class.simpleName}.ARG_LOGIN_PROFILE"
        val BACK_STACK = "${MemberListActivity::class.simpleName}.BACK_STACK"
    }

    val loginProfile by lazy { Parcels.unwrap<LoginProfile>(intent.getParcelableExtra(ARG_LOGIN_PROFILE))!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)
        setSupportActionBar(toolbar)
        val fragment = MemberListFragment().withArguments(MemberListFragment.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile))
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
                    PostListFragment.ARG_LOGIN_PROFILE to Parcels.wrap(loginProfile))
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

    class MembersPostListFragment : PostListFragment() {

        companion object {
            val ARG_SCREEN_NAME = "${MembersPostListFragment::class.qualifiedName}.ARG_SCREEN_NAME"
        }

        val screenName by lazy { arguments!!.getString(ARG_SCREEN_NAME) }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            EsaAccessService.getPosts(context, loginProfile, "@$screenName").let { context.startService(it) }
        }

        override fun loadPosts(): RealmResults<Post>? {
            val team: Team? = realm.where(Team::class.java)
                    .equalTo("loginToken", loginProfile.token)
                    .findFirst()
            val member: Member? = realm.where(Member::class.java)
                    .equalTo("screenName", screenName)
                    .findFirst()
            if (team != null && member != null) {
                return team.posts?.where()
                        ?.equalTo("createdBy.screenName", member.screenName)
                        ?.findAllSorted("updatedAt", Sort.DESCENDING)
            }
            return null
        }

    }
}
