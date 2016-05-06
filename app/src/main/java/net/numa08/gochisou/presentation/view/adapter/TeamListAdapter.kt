package net.numa08.gochisou.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.RealmBaseAdapter
import io.realm.RealmResults
import net.numa08.gochisou.data.model.Team

class TeamListAdapter(context: Context, realmResults: RealmResults<Team>?) : RealmBaseAdapter<Team>(context, realmResults) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = (convertView as? TextView) ?: LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        textView.text = getItem(position).name
        return textView
    }
}
