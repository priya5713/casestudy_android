package com.casestudy.demo.util

import android.app.Activity
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.casestudy.demo.R

object BindingAdapterUtil {
    @JvmStatic
    @BindingAdapter("url")
    fun loadImage(view: ImageView, url: String?) {
        url?.let {
            if (view.context != null) {
                try {
                    if (view.context is Activity) {
                        if ((view.context as Activity).isFinishing) {
                            return
                        }
                    }
                } catch (e: Exception) {
                }
                Glide.with(view.context).load(url).apply(RequestOptions.circleCropTransform()).apply(RequestOptions().placeholder(R.drawable.ic_user_placeholder)).into(view)
            }
        }
    }
}
