package net.numa08.gochisou.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.presentation.presenter.InputTeamURLPresenter
import net.numa08.gochisou.presentation.view.fragment.InputTeamURLFragment
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), InputTeamURLFragment.PresenterProvider {

    companion object{
        val BACK_STACK = "${LoginActivity::class.simpleName}.BACK_STACK"

        fun intent(ctx: Context): Intent =
            Intent(ctx, LoginActivity::class.java)
    }

    override var presenter: InputTeamURLPresenter? = null
        @Inject set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        presenter?.fragmentManager = supportFragmentManager
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
        presenter?.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter?.pause()
    }
}