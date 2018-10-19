package com.casestudy.demo.util

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import com.casestudy.demo.R

fun Context?.appToast(msg: String?, duration: Int = Toast.LENGTH_SHORT) = makeToast(this, msg, duration)

fun Context?.appToast(resourceId: Int, duration: Int = Toast.LENGTH_SHORT) = makeToast(this, resourceId, duration)

private fun makeToast(ctx: Context?, msg: Any?, duration: Int) {
    if (ctx == null || msg == null || (msg is String && msg.isEmpty())) return
    val m = if (msg is String) msg else ctx.resources.getString(msg as Int)
    val toast = Toast.makeText(ctx, m, duration)
    toast.show()
}


fun Context?.isInternet(): Boolean {
    if (this == null) {
        return false
    }

    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetworkInfo
    val available = network != null && network.isConnected
    if (!available) {
        appToast(R.string.please_check_your_internet_connectivity)
    }
    return available
}

fun View.show(): Boolean {
    return visibility == View.VISIBLE
}

fun View.hide(): Boolean {
    return visibility == View.GONE
}