package com.example.core.interfaces

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageLoader {
    fun load(imageView: ImageView, options: ImageLoaderOptions)
    fun getBitmap(context: Context, options: ImageBitmapOptions)
}

data class ImageLoaderOptions(
    val placeholderResource: Int? = null,
    val resourceOnError: Int? = null,
    val resourceOnFallback: Int? = null,
    val resourceUri: Any?,
    val resizeDimen: Int? = null,
    val shouldBeRounded: Boolean = false,
    val centerInside: Boolean = false,
    val centerCrop: Boolean = false,
    val fitCenter: Boolean = false,
    val crossFadeTransition: Boolean = true,
    val downsample: Boolean = false,
    var onLoadFailedListener: ((Exception?) -> Boolean)? = null,
    var onLoadReadyListener: ((Drawable?) -> Boolean)? = null
)

data class ImageBitmapOptions(
    val resourceUri: Any?,
    val resizeDimen: Int? = null,
    val shouldBeRounded: Boolean = false,
    var onLoadFailedListener: (Exception?) -> Boolean,
    var onLoadReadyListener: (Bitmap?) -> Boolean
)