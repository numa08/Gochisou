package net.numa08.gochisou.presentation.widget

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

@Suppress("unused")
class BottomBarBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<TabLayout>(context, attrs) {

    var defaultDependencyTop = -1

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: TabLayout?, dependency: View?): Boolean =
            dependency is AppBarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: TabLayout?, dependency: View?): Boolean {
        if (defaultDependencyTop == -1) {
            defaultDependencyTop = dependency?.top ?: 0
        }
        ViewCompat.setTranslationY(child, defaultDependencyTop.toFloat() - (dependency?.y ?: 0.0f))
        return true
    }

}