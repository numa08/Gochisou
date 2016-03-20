package net.numa08.gochisou.presentation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.repositories.LoginProfileRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var loginProfileRepository: LoginProfileRepository
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
        setContentView(R.layout.activity_main)
        if(loginProfileRepository.isEmpty()) {
            startActivity(LoginActivity.intent(this))
            supportFinishAfterTransition()
        } else {
            Toast.makeText(this, "loginned", Toast.LENGTH_LONG).show()
        }
    }

}