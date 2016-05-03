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
import kotlinx.android.synthetic.main.fragment_input_team_name.*
import net.numa08.gochisou.R

class InputTeamNameFragment : RxFragment() {

    interface Callback {
        fun onClickNext(fragment: InputTeamNameFragment, teamName: String)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_input_team_name, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changeNextButtonAvailability = { button_next.isEnabled = !TextUtils.isEmpty(edit_team_name.text) }

        changeNextButtonAvailability()
        edit_team_name
        .textChanges()
        .compose(bindToLifecycle<CharSequence>())
        .subscribe{changeNextButtonAvailability()}

        button_next
        .clicks()
        .compose(bindToLifecycle<Unit>())
                .subscribe({
                    (activity as Callback).onClickNext(this, edit_team_name.text.toString())
                }, {
                    Log.e("Gochisou", "thorw error on click next button ", it)
                })

    }
}