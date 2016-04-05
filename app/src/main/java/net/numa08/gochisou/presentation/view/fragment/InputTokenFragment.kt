package net.numa08.gochisou.presentation.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.widget.textChanges
import com.trello.rxlifecycle.components.support.RxFragment
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.LoginProfile
import net.numa08.gochisou.presentation.presenter.InputTokenPresenter
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.browse
import org.parceler.Parcels

class InputTokenFragment: RxFragment() {
    companion object {
        val ARG_LOGIN_PROFILE = "${InputTokenFragment::class.simpleName}.ARG_LOGIN_PROFILE"
    }

    interface PresenterProvider {
        val inputTokenPresenter: InputTokenPresenter
    }

    val loginProfile by lazy { Parcels.unwrap<LoginProfile>(arguments!!.getParcelable(ARG_LOGIN_PROFILE)) }

    val getTokenButton by lazy {view!!.find<Button>(R.id.button_get_token)}
    val tokenText by lazy {view!!.find<EditText>(R.id.edit_token)}
    val loginButton by lazy {view!!.find<Button>(R.id.button_login)}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_input_token, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenText
        .textChanges()
        .compose(bindToLifecycle<CharSequence>())
        .subscribe{ loginButton.isEnabled = !TextUtils.isEmpty(tokenText.text)}

        getTokenButton
        .clicks()
        .compose(bindToLifecycle<Unit>())
        .subscribe{browse("https://${loginProfile?.teamURL}.esa.io/user/tokens")}

        loginButton
        .clicks()
        .compose(bindToLifecycle<Unit>())
        .subscribe {
            if (activity != null) {
                (activity as? PresenterProvider)?.let { it.inputTokenPresenter.onClickLogin(LoginProfile(loginProfile.teamURL, tokenText.text.toString())) }
                        ?: Log.d("Gochisou", "Activity should implement PresenterProvider")
            } else {
                Log.d("Gochisou", "activity is null")
            }
        }
    }
}