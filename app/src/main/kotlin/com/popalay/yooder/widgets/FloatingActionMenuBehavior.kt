package com.popalay.yooder.widgets

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

import com.github.clans.fab.FloatingActionMenu

class FloatingActionMenuBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>() {

    private var mTranslationY: Float = 0.toFloat()

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        return dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View?, dependency: View?): Boolean {
        if (child is FloatingActionMenu && dependency is Snackbar.SnackbarLayout) {
            this.updateTranslation(parent, child, dependency)
        }
        return false
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View?, dependency: View?) {
        if (child is FloatingActionMenu && dependency is Snackbar.SnackbarLayout) {
            this.updateTranslation(parent, child, dependency)
        }
    }

    /**
     * onStartNestedScroll and onNestedScroll will hide/show the FabMenu when a scroll is detected.
     */
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?,
                                     directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                nestedScrollAxes)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View?) {
        super.onStopNestedScroll(coordinatorLayout, child, target)
        val fabMenu = child as FloatingActionMenu
        fabMenu.showMenuButton(true)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View?,
                                dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed)
        val fabMenu = child as FloatingActionMenu
        if (dyConsumed > 0 && !fabMenu.isMenuButtonHidden) {
            fabMenu.hideMenuButton(true)
        } else if (dyConsumed < 0 && !fabMenu.isMenuButtonHidden) {
            fabMenu.hideMenuButton(true)
        }
    }

    private fun updateTranslation(parent: CoordinatorLayout, child: View, dependency: View) {
        val translationY = this.getTranslationY(parent, child)
        if (translationY != this.mTranslationY) {
            ViewCompat.animate(child).cancel()
            if (Math.abs(translationY - this.mTranslationY) == dependency.height.toFloat()) {
                ViewCompat.animate(child).translationY(translationY).setListener(null)
            } else {
                ViewCompat.setTranslationY(child, translationY)
            }
            this.mTranslationY = translationY
        }

    }

    private fun getTranslationY(parent: CoordinatorLayout, child: View): Float {
        var minOffset = 0.0f
        val dependencies = parent.getDependencies(child)
        var i = 0
        val z = dependencies.size
        while (i < z) {
            if (dependencies[i] is Snackbar.SnackbarLayout && parent.doViewsOverlap(child, dependencies[i])) {
                minOffset = Math.min(minOffset,
                        ViewCompat.getTranslationY(dependencies[i]) - dependencies[i].height.toFloat())
            }
            ++i
        }
        return minOffset
    }
}
