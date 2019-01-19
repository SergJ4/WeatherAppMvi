package com.example.core.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.core.R
import org.jetbrains.anko.dip

fun SwipeRefreshLayout.applyDefaultStyle(progressViewOffset: Int = 60) {
    setSize(SwipeRefreshLayout.DEFAULT)
    setColorSchemeResources(R.color.primary, R.color.primaryDark)
    setDistanceToTriggerSync(200)
    setProgressViewOffset(true, 0, dip(progressViewOffset))
}