package net.numa08.gochisou.presentation.view.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.realm.RealmBaseRecyclerAdapter
import io.realm.RealmResults
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.Member
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

class MemberListAdapter(members: RealmResults<Member>?, val picasso: Picasso) : RealmBaseRecyclerAdapter<Member, MemberListAdapter.ViewHolder>(members, true) {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.text?.let {
            it.text = realmResults?.get(position)?.screenName
            it.textColor = ContextCompat.getColor(it.context, R.color.text)
            picasso.load(realmResults?.get(position)?.icon)
                    .into(object : Target {
                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            Log.d("Gochisou", "onPrepareLoad")
                        }

                        override fun onBitmapFailed(errorDrawable: Drawable?) {
                            Log.e("Gochisou", "onBitampFailed")
                        }

                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            Log.d("Gochisou", "onBitampLoaded")
                            it.setCompoundDrawablesRelativeWithIntrinsicBounds(BitmapDrawable(it.context.resources, bitmap), null, null, null)
                        }
                    })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent?.context)?.inflate(R.layout.row_team_list, parent, false)!!
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = realmResults?.size ?: 0

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val text = view.find<TextView>(android.R.id.text1)
    }


}