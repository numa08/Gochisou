package net.numa08.gochisou.presentation.view.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.daimajia.swipe.interfaces.SwipeAdapterInterface
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface
import com.daimajia.swipe.util.Attributes
import com.squareup.picasso.Picasso
import io.realm.RealmBaseRecyclerAdapter
import io.realm.RealmResults
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.Post
import org.jetbrains.anko.find

open class PostListAdapter(posts: RealmResults<Post>?, val picasso: Picasso) : RealmBaseRecyclerAdapter<Post, ViewHolder>(posts, true), SwipeItemMangerInterface, SwipeAdapterInterface {
    val itemManager = SwipeItemRecyclerMangerImpl(this)

    init {
        mode = Attributes.Mode.Single
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if(realmResults != null && holder != null) {
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.leftContent)
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.rightContent)
            picasso.load(realmResults?.get(position)?.createdBy?.icon)
                    .into(holder.avatarImage)
            holder.category.text = realmResults?.get(position)?.category
            holder.textName.text = realmResults?.get(position)?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent?.context)?.inflate(R.layout.row_post, parent, false) as CardView
        return ViewHolder(view)
    }

    override fun getSwipeLayoutResourceId(position: Int): Int = R.id.swipe_layout

    override fun getItemCount(): Int = realmResults?.size ?: 0

    override fun closeAllExcept(layout: SwipeLayout?) {
        itemManager.closeAllExcept(layout)
    }

    override fun setMode(mode: Attributes.Mode?) {
        itemManager.mode = mode
    }

    override fun closeAllItems() {
        itemManager.closeAllItems()
    }

    override fun removeShownLayouts(layout: SwipeLayout?) {
        itemManager.removeShownLayouts(layout)
    }

    override fun getOpenItems(): MutableList<Int>? {
        return itemManager.openItems
    }

    override fun isOpen(position: Int): Boolean {
        return itemManager.isOpen(position)
    }

    override fun openItem(position: Int) {
        itemManager.openItem(position)
    }

    override fun getMode(): Attributes.Mode? {
        return itemManager.mode
    }

    override fun getOpenLayouts(): MutableList<SwipeLayout>? {
        return itemManager.openLayouts
    }

    override fun closeItem(position: Int) {
        itemManager.closeItem(position)
    }
}

class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
    val swipeLayout = cardView.find<SwipeLayout>(R.id.swipe_layout)
    val leftContent = swipeLayout.find<View>(R.id.left_content)
    val centerContent = swipeLayout.find<View>(R.id.center_content)
    val rightContent = swipeLayout.find<View>(R.id.right_content)
    val avatarImage = centerContent.find<ImageView>(R.id.image_author_avatar)
    val category = centerContent.find<TextView>(R.id.text_category)
    val textName = centerContent.find<TextView>(R.id.text_name)
}