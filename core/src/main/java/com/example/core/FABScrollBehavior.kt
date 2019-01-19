package com.example.core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Hides and shows FAB depending on RecyclerView`s scroll direction
 */
class FABScrollBehavior(context: Context, attributeSet: AttributeSet) :
    FloatingActionButton.Behavior(context, attributeSet) {

    private var recyclerView: RecyclerView? = null

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ): Boolean {
        fun handleFABVisibility(dyConsumed: Int) {
            if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
                child.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                    override fun onHidden(fab: FloatingActionButton) {
                        super.onHidden(fab)
                        fab.hide()
                    }
                })
            } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
                child.show()
            }
        }

        if (recyclerView == null && dependency is RecyclerView) {
            recyclerView = dependency
            dependency.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    handleFABVisibility(dy)
                }
            })
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}