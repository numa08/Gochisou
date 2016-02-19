package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.trello.rxlifecycle.components.support.RxFragment
import io.realm.Realm
import io.realm.RealmConfiguration
import net.numa08.gochisou.GochisouApplication
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.PageNation
import net.numa08.gochisou.data.model.Team
import net.numa08.gochisou.data.service.EsaService
import net.numa08.gochisou.presentation.internal.di.PerActivity
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@EFragment(R.layout.fragment_login)
open class LoginFragment : RxFragment() {

    @ViewById(R.id.token)
    @JvmField
    var tokenEditText: EditText? = null

    @PerActivity
    var esaService: EsaService? = null
      @Inject set
    @PerActivity
    var realmConfiguration: RealmConfiguration? = null
      @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
    }

    @Click(R.id.login)
    fun onClickLogin() {
        val token = tokenEditText?.text.toString()
        esaService
        ?.teams(token)
        ?.compose(bindToLifecycle<PageNation.TeamPageNation>())
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.map{ it.list ?: emptyList<Team>()}
        ?.subscribe({ tl ->
            Realm.getInstance(realmConfiguration).use{
                it.executeTransaction { it.copyToRealmOrUpdate(tl) }
            }
        }, {
            Log.e("Gochisou", "load team error", it)
            Toast.makeText(context, "Cloud not get teams", Toast.LENGTH_LONG).show()
        })
    }

}
