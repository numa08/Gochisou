package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class LoginFragment : RxFragment() {

    @PerActivity
    var esaService: EsaService? = null
      @Inject set
    @PerActivity
    var realmConfiguration: RealmConfiguration? = null
      @Inject set
    val tokenEditText by lazy { view?.findViewById(R.id.token) as? EditText }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GochisouApplication.application?.applicationComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_login, container, false)
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
