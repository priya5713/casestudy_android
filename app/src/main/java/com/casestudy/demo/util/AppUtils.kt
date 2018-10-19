package com.casestudy.demo.util

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Base64
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object AppUtils {

    val currentDateTime: Date
        get() = Calendar.getInstance().time

    fun getFormattedDateString(date: Date): String? {

        try {
            var spf = SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy")
            val dateString = spf.format(date)

            val newDate = spf.parse(dateString)
            spf = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
            return spf.format(newDate)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    fun generateHash(password: String): String? {
        var md: MessageDigest? = null
        try {
            md = MessageDigest.getInstance("SHA-512")
            md!!.update(password.toByteArray())
            val byteData = md.digest()
            return Base64.encodeToString(byteData, Base64.NO_WRAP)

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }

    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun openKeyboard(context: Context) {
        Handler().postDelayed({
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }, 500)
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }
}
