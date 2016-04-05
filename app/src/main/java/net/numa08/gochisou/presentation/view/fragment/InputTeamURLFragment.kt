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
import net.numa08.gochisou.presentation.presenter.InputTeamURLPresenter
import org.jetbrains.anko.find

class InputTeamURLFragment : RxFragment(){

    interface PresenterProvider{
        val inputTeamURLPresenter: InputTeamURLPresenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_input_team_url, container, false)
    }

    val teamText by lazy { view!!.find<EditText>(R.id.edit_team_name) }
    val nextButton by lazy {view!!.find<Button>(R.id.button_next) }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changeNextButtonAvailability = {nextButton.isEnabled = !TextUtils.isEmpty(teamText.text)}

        changeNextButtonAvailability()
        teamText
        .textChanges()
        .compose(bindToLifecycle<CharSequence>())
        .subscribe{changeNextButtonAvailability()}

        nextButton
        .clicks()
        .compose(bindToLifecycle<Unit>())
        .subscribe{(activity as? PresenterProvider)?.inputTeamURLPresenter?.onClickNext(LoginProfile(teamText.text.toString()))
                ?: Log.d("Gochisou", "activity should implements PresenterProvider")
        }

    }
}