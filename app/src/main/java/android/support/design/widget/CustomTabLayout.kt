package android.support.design.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class CustomTabLayout : TabLayout {
    interface CustomPagerAdapter {
        fun customTabView(position: Int, parent: ViewGroup?): View?
    }

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : super(context, attrs, defStyleAttr) {
    }

    override fun setupWithViewPager(viewPager: ViewPager?) {
        super.setupWithViewPager(viewPager)
        (viewPager?.adapter as? CustomPagerAdapter)?.let { adapter ->
            repeat(viewPager?.adapter?.count ?: 0) { i ->
                val tab = getTabAt(i)
                val v = adapter.customTabView(i, this)
                tab?.customView = v
            }
        }
    }

}