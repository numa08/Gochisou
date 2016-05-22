package net.numa08.gochisou.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import net.numa08.gochisou.data.model.Team

class TeamListAdapter(context: Context, items: List<Team>?) : ArrayAdapter<Team>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = (convertView as? TextView) ?: LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        textView.text = getItem(position).name
        return textView
    }
}
