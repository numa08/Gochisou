package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.widget.textChanges
import com.trello.rxlifecycle.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_input_token.*
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.Client
import org.jetbrains.anko.support.v4.browse

class InputTokenFragment: RxFragment() {
    companion object {
        val ARG_TEAM_URL = "${InputTokenFragment::class.simpleName}.ARG_TEAM_URL"
    }

    interface Callback {
        fun onClickLogin(fragment: InputTokenFragment, teamName: String, client: Client, redirectURL: String)
    }

    val teamName by lazy { arguments!!.getString(ARG_TEAM_URL) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_input_token, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changeLoginButtonAvailability = {
            button_login.isEnabled = !TextUtils.isEmpty(edit_client_id.text)
                    && !TextUtils.isEmpty(edit_client_secret.text)
                    && !TextUtils.isEmpty(edit_redirect_url.text)
        }
        edit_client_secret
        .textChanges()
        .compose(bindToLifecycle<CharSequence>())
                .subscribe { changeLoginButtonAvailability() }

        edit_client_id
                .textChanges()
                .compose(bindToLifecycle<CharSequence>())
                .subscribe { changeLoginButtonAvailability() }

        edit_redirect_url
                .textChanges()
                .compose(bindToLifecycle<CharSequence>())
                .subscribe { changeLoginButtonAvailability() }

        button_get_token
        .clicks()
        .compose(bindToLifecycle<Unit>())
                .subscribe { browse("https://$teamName.esa.io/user/applications") }

        button_login
                .clicks()
                .compose(bindToLifecycle<Unit>())
                .subscribe({
                    (activity as Callback).onClickLogin(this, teamName, Client(edit_client_id.text.toString(), edit_client_secret.text.toString()), edit_redirect_url.text.toString())
                }, {
                    Log.e("Gochisou", "throw error on click login", it)
                })
    }
}