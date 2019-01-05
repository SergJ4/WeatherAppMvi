package com.example.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.core.interfaces.ImageBitmapOptions
import com.example.core.interfaces.ImageLoader
import com.example.core.interfaces.ImageLoaderOptions
import javax.inject.Inject

class ImageLoaderImpl @Inject constructor() : ImageLoader {

    override fun load(imageView: ImageView, options: ImageLoaderOptions) {
        val request = GlideApp
            .with(imageView)
            .load(options.resourceUri)

        options.placeholderResource?.let {
            request.placeholder(it)
        }

        options.resourceOnError?.let {
            request.error(it)
        }

        options.resourceOnFallback?.let {
            request.fallback(it)
        }

        if (options.shouldBeRounded) {
            request.circleCrop()
        }

        if (options.centerInside) {
            request.centerInside()
        }

        if (options.centerCrop) {
            request.centerCrop()
        }

        if (options.fitCenter) {
            request.fitCenter()
        }

        if (options.crossFadeTransition) {
            request.transition(DrawableTransitionOptions.withCrossFade())
        }

        if (options.downsample) {
            request.downsample(DownsampleStrategy.CENTER_INSIDE)
        }

        if (options.onLoadFailedListener != null || options.onLoadReadyListener != null) {
            request.listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return options.onLoadFailedListener?.invoke(e) ?: false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return options.onLoadReadyListener?.invoke(resource) ?: false
                }
            })
        }

        options.resizeDimen?.let {
            request.override(it)
        }
        request.into(imageView)
    }

    override fun getBitmap(context: Context, options: ImageBitmapOptions) {
        val request = GlideApp
            .with(context)
            .asBitmap()
            .load(options.resourceUri)

        if (options.shouldBeRounded) {
            request.circleCrop()
        }

        options.resizeDimen?.let {
            request.override(it)
        }

        request.listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                return options.onLoadFailedListener(e)
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return options.onLoadReadyListener(resource)
            }
        })

        request.submit()
    }
}