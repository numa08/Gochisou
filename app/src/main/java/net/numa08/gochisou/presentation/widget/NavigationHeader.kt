package net.numa08.gochisou.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_navigation_header.view.*
import net.numa08.gochisou.R
import net.numa08.gochisou.data.model.Team
import org.jetbrains.anko.onClick

class NavigationHeader(context: Context, attributeSet: AttributeSet) : RelativeLayout(context, attributeSet) {
    interface ActionListener {
        fun onClickPostList(header: NavigationHeader?)
        fun onClickUserList(header: NavigationHeader?)
    }

    internal var alreadyInflated = false

    override fun onFinishInflate() {
        if (!alreadyInflated) {
            alreadyInflated = true
            inflate(context, R.layout.view_navigation_header, this)
            init()
        }
        super.onFinishInflate()
    }

    var team: Team? = null
        set(value) {
            value?.let { v ->
                Picasso
                        .with(context)
                        .load(v.icon)
                        .into(image_navigation_header)
                text_navigation_header_title.text = v.name
            }
        }

    var listener: ActionListener? = null

    private fun init() {
        button_posts.onClick { listener?.onClickPostList(this) }
        button_users.onClick { listener?.onClickUserList(this) }
    }
}