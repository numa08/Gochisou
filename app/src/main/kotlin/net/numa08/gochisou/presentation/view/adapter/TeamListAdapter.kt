package net.numa08.gochisou.presentation.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import net.numa08.gochisou.data.model.Team

import io.realm.RealmBaseAdapter
import io.realm.RealmResults

class TeamListAdapter(context: Context, realmResults: RealmResults<Team>?) : RealmBaseAdapter<Team>(context, realmResults, true) {

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val textView = TextView(parent.context)
        textView.text = realmResults[position].name
        return textView
    }
}
