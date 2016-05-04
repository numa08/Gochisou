package net.numa08.gochisou.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import net.numa08.gochisou.BuildConfig
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.*
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.data.repositories.TempLoginInfoRepository
import net.numa08.gochisou.data.service.AuthorizeURLGenerator
import net.numa08.gochisou.presentation.presenter.LoginPresenter
import net.numa08.gochisou.presentation.view.fragment.InputTeamNameFragment
import net.numa08.gochisou.presentation.view.fragment.InputTokenFragment
import org.jetbrains.anko.browse
import org.jetbrains.anko.support.v4.withArguments
import javax.inject.Inject

class LoginActivity : AppCompatActivity(),
        InputTeamNameFragment.Callback,
        InputTokenFragment.Callback,
        LoginPresenter.Callback {

    companion object{
        val BACK_STACK = "${LoginActivity::class.simpleName}.BACK_STACK"

        fun intent(ctx: Context): Intent =
            Intent(ctx, LoginActivity::class.java)

        val OAUTH_STATE = BuildConfig.APPLICATION_ID
    }

    lateinit var profileRepository: LoginProfileRepository
        @Inject set
    lateinit  var realmConfiguration: RealmConfiguration
        @Inject set
    lateinit var navigationIdentifierRepository: NavigationIdentifierRepository
        @Inject set
    lateinit var urlGenerator: AuthorizeURLGenerator
        @Inject set
    lateinit var tempLoginInfoRepository: TempLoginInfoRepository
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
                .add(R.id.content, InputTeamNameFragment(), BACK_STACK)
        .commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.data
        val tempLoginInfo = uri?.getQueryParameter("state")?.let { tempLoginInfoRepository[it] }
        val code = uri?.getQueryParameter("code")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount == 1) {
            supportFinishAfterTransition()
        }
    }

    override fun onClickNext(fragment: InputTeamNameFragment, teamName: String) {
        val nextFragment = InputTokenFragment().withArguments(InputTokenFragment.ARG_TEAM_URL to teamName)
        supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.content, nextFragment, LoginActivity.BACK_STACK)
                ?.addToBackStack(LoginActivity.BACK_STACK)
                ?.commit()
    }

    override fun onClickLogin(fragment: InputTokenFragment, teamName: String, client: Client, redirectURL: String) {
        tempLoginInfoRepository[OAUTH_STATE] = TempLoginInfo(teamName, client, redirectURL)
        val url = urlGenerator.generateAuthorizeURL(client.id, redirectURL, state = OAUTH_STATE)
        browse(url)
    }

    override fun onLogin(loginProfile: LoginProfile, team: PageNation.TeamPageNation) {
        profileRepository.add(loginProfile)
        val teams = team
                .list
                ?.map { it.loginToken = loginProfile.token.accessToken; it }
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