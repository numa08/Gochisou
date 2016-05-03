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
import net.numa08.gochisou.data.model.LoginProfile
import org.jetbrains.anko.support.v4.browse

class InputTokenFragment: RxFragment() {
    companion object {
        val ARG_TEAM_URL = "${InputTokenFragment::class.simpleName}.ARG_TEAM_URL"
    }

    interface Callback {
        fun onClickLogin(fragment: InputTokenFragment, loginProfile: LoginProfile)
    }

    val teamURL by lazy { arguments!!.getString(ARG_TEAM_URL) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_input_token, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_token
        .textChanges()
        .compose(bindToLifecycle<CharSequence>())
                .subscribe { button_login.isEnabled = !TextUtils.isEmpty(edit_token.text) }

        button_get_token
        .clicks()
        .compose(bindToLifecycle<Unit>())
                .subscribe { browse("https://$teamURL.esa.io/user/tokens") }

        button_login
        .clicks()
        .compose(bindToLifecycle<Unit>())
                .subscribe({
                    (activity as Callback).onClickLogin(this, LoginProfile(teamURL, edit_token.text.toString()))
                }, {
                    Log.e("Gochisou", "throw error on click login", it)
                })
    }
}