package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.jakewharton.rxbinding.view.clicks
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
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.lang.kotlin.*
import javax.inject.Inject

@EFragment(R.layout.fragment_login)
open class LoginFragment : RxFragment() {

    @ViewById(R.id.token)
    @JvmField
    var tokenEditText: EditText? = null
    @ViewById(R.id.get_token)
    @JvmField
    var getFieldButton: Button? = null

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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view
        ?.findViewById(R.id.login)
        ?.clicks()
        ?.switchMap {esaService?.teams(tokenEditText?.text.toString())}
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
