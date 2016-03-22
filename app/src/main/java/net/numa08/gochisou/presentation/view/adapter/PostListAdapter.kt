package net.numa08.gochisou.presentation.view.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.realm.RealmBaseRecyclerAdapter
import io.realm.RealmResults
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.Post
import org.jetbrains.anko.find

class PostListAdapter(posts: RealmResults<Post>?, val picasso: Picasso) : RealmBaseRecyclerAdapter<Post, ViewHolder>(posts,true) {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if(realmResults != null && holder != null) {
            picasso.load(realmResults?.get(position)?.createdBy?.icon)
                    .into(holder.avatarImage)
            holder.textFullName.text = realmResults?.get(position)?.fullName
            holder.textName.text = realmResults?.get(position)?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent?.context)?.inflate(R.layout.row_post, parent, false) as CardView
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = realmResults?.size ?: 0
}

class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
    val avatarImage = cardView.find<ImageView>(R.id.image_author_avatar)
    val textFullName = cardView.find<TextView>(R.id.text_full_name)
    val textName = cardView.find<TextView>(R.id.text_name)
}