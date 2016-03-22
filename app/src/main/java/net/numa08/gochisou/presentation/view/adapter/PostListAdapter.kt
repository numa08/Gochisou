package net.numa08.gochisou.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.RealmBaseAdapter
import io.realm.RealmResults
import net.numa08.gochisou.data.model.Post

class PostListAdapter(context: Context, posts: RealmResults<Post>?) : RealmBaseAdapter<Post>(context, posts,true) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val post = getItem(position)
        val textView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        textView.text = post.name
        return textView
    }


}