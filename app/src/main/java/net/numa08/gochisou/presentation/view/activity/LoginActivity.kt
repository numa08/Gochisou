package net.numa08.gochisou.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.model.PageNation
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.presentation.presenter.LoginPresenter
import net.numa08.gochisou.presentation.view.fragment.InputTeamURLFragment
import net.numa08.gochisou.presentation.view.fragment.InputTokenFragment
import org.jetbrains.anko.support.v4.withArguments
import javax.inject.Inject

class LoginActivity : AppCompatActivity(),
        InputTeamURLFragment.Callback,
        InputTokenFragment.Callback,
        LoginPresenter.Callback {

    companion object{
        val BACK_STACK = "${LoginActivity::class.simpleName}.BACK_STACK"

        fun intent(ctx: Context): Intent =
            Intent(ctx, LoginActivity::class.java)
    }

    lateinit var profileRepository: LoginProfileRepository
        @Inject set
    lateinit  var realmConfiguration: RealmConfiguration
        @Inject set
    lateinit var navigationIdentifierRepository: NavigationIdentifierRepository
        @Inject set
    lateinit var loginPresenter: LoginPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        loginPresenter = GochisouApplication.application?.applicationComponent?.activityComponent()?.loginPresenter()!!
        loginPresenter.callback = this

        setContentView(R.layout.activity_login)
        supportFragmentManager
        .beginTransaction()
        .add(R.id.content, InputTeamURLFragment(), BACK_STACK)
        .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount == 1) {
            supportFinishAfterTransition()
        }
    }

    override fun onClickNext(fragment: InputTeamURLFragment, teamURL: String) {
        val nextFragment = InputTokenFragment().withArguments(InputTokenFragment.ARG_TEAM_URL to teamURL)
        supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.content, nextFragment, LoginActivity.BACK_STACK)
                ?.addToBackStack(LoginActivity.BACK_STACK)
                ?.commit()
    }

    override fun onClickLogin(fragment: InputTokenFragment, loginProfile: LoginProfile) {
        loginPresenter.login(loginProfile)
    }

    override fun onLogin(loginProfile: LoginProfile, team: PageNation.TeamPageNation) {
        profileRepository.add(loginProfile)
        val teams = team
                .list
                ?.map { it.loginToken = loginProfile.token; it }
                ?.map { navigationIdentifierRepository.add(NavigationIdentifier.PostNavigationIdentifier(it.name ?: "", it.icon ?: "", loginProfile)); it }

        Realm.getInstance(realmConfiguration)?.use {
            it.executeTransaction { it.copyToRealmOrUpdate(teams) }
        }

        startActivity(Intent(this, MainActivity::class.java))
        supportFinishAfterTransition()
    }

    override fun onFailure(throwable: Throwable) {
        Log.e("Gochisou", "failed login ", throwable)
    }
}