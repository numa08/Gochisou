package net.numa08.gochisou.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import net.numa08.gochisou.data.model.Team

import io.realm.RealmBaseAdapter
import io.realm.RealmResults

class TeamListAdapter(context: Context, realmResults: RealmResults<Team>?) : RealmBaseAdapter<Team>(context, realmResults, true) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        textView.text = realmResults[position].name
        return textView
    }
}
