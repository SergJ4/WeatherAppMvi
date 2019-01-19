package com.example.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.core.extensions.applyDefaultStyle
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.findOptional

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allowEnterTransitionOverlap = true
        allowReturnTransitionOverlap = true
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (view != null) {
            if (nextAnim == R.anim.slide_in_right || nextAnim == R.anim.slide_out_right) {
                ViewCompat.setTranslationZ(view!!, 1f)
            } else {
                ViewCompat.setTranslationZ(view!!, 0f)
            }
        }

        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findOptional<SwipeRefreshLayout>(R.id.swipeRefresh)?.applyDefaultStyle()
    }

    fun showProgress() {
        view?.findOptional<SwipeRefreshLayout>(R.id.swipeRefresh)
            ?.isRefreshing = true
    }

    fun hideProgress() {
        view?.findOptional<SwipeRefreshLayout>(R.id.swipeRefresh)
            ?.isRefreshing = false
    }

    fun showMessage(message: String) {
        if (view != null) {
            val snackbar = Snackbar.make(
                view!!,
                message,
                Snackbar.LENGTH_LONG
            )

            snackbar.show()
        }
    }

}