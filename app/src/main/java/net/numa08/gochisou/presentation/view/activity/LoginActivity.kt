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
import net.numa08.gochisou.data.model.NavigationIdentifier
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import net.numa08.gochisou.data.repositories.NavigationIdentifierRepository
import net.numa08.gochisou.presentation.view.fragment.InputTeamURLFragment
import net.numa08.gochisou.presentation.view.fragment.InputTokenFragment
import javax.inject.Inject

class LoginActivity : AppCompatActivity(),
        InputTeamURLFragment.PresenterProvider,
        InputTokenFragment.PresenterProvider {

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
    override val inputTeamURLPresenter by lazy { GochisouApplication.application?.applicationComponent?.activityComponent()?.inputTeamURLPresenter()!! }
    override val inputTokenPresenter by lazy { GochisouApplication.application?.applicationComponent?.activityComponent()?.inputTokenPresenter()!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        inputTeamURLPresenter.fragmentManager = supportFragmentManager
        inputTokenPresenter.onLoginHandler = { c ,p, r ->
            Log.d("Gochisou", "on login")
            profileRepository.add(p)
            val ts = r?.body()?.list?.map{ it.loginToken = p.token; it }
            ts?.forEach { navigationIdentifierRepository.add(NavigationIdentifier.PostNavigationIdentifier(it.name ?: "", it.icon ?: "", p)) }
            Realm.getInstance(realmConfiguration).use { re ->
                re.executeTransaction { it.copyToRealmOrUpdate(ts) }

            }
            startActivity(Intent(this, MainActivity::class.java))
            supportFinishAfterTransition()
        }
        inputTokenPresenter.onErrorHandler = {c, e ->
            Log.e("Gochisou", "Could not login", e)
            Log.d("Gochisou", "caller header is " + c?.request()?.headers())
            Log.d("Gochisou", "caller body is " + c?.request()?.body())
        }

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

    override fun onResume() {
        super.onResume()
        inputTeamURLPresenter.resume()
        inputTokenPresenter.resume()
    }

    override fun onPause() {
        super.onPause()
        inputTeamURLPresenter.pause()
        inputTokenPresenter.pause()
    }
}