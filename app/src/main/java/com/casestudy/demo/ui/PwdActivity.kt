package com.casestudy.demo.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.casestudy.demo.R
import com.casestudy.demo.model.Note
import com.casestudy.demo.util.AppConstants
import com.casestudy.demo.util.AppConstants.Companion.INTENT_TASK
import com.casestudy.demo.util.AppUtils
import com.casestudy.demo.util.NavigatorUtils

class PwdActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener, AppConstants {

    private var toolbarTitle: TextView? = null
    private var btnDone: TextView? = null
    private var btnClose: ImageView? = null
    private var editPwd: EditText? = null

    private var note: Note? = null
    private var pwdVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwd)

        toolbarTitle = findViewById(R.id.title)
        toolbarTitle!!.text = getString(R.string.toolbar_pwd)

        btnClose = findViewById(R.id.btn_close)
        btnClose!!.setOnClickListener(this)

        btnDone = findViewById(R.id.btn_done)
        btnDone!!.setOnClickListener(this)

        editPwd = findViewById(R.id.edit_pwd)
        editPwd!!.setOnTouchListener(this)

        note = intent.getSerializableExtra(INTENT_TASK) as Note
        AppUtils.openKeyboard(applicationContext)
    }


    private fun togglePwd() {
        if (!pwdVisible) {
            pwdVisible = java.lang.Boolean.TRUE
            editPwd!!.transformationMethod = null
            val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_pwd)!!.mutate()
            drawable.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(applicationContext, R.color.line), PorterDuff.Mode.MULTIPLY)
            editPwd!!.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

        } else {
            pwdVisible = java.lang.Boolean.FALSE
            editPwd!!.transformationMethod = PasswordTransformationMethod()
            val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_pwd)!!.mutate()
            drawable.colorFilter = PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)
            editPwd!!.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        }
        editPwd!!.setSelection(editPwd!!.length())
    }


    override fun onClick(view: View) {
        AppUtils.hideKeyboard(this)

        if (view === btnClose) {
            finish()
            overridePendingTransition(R.anim.stay, R.anim.slide_down)

        } else if (view === btnDone) {
            //Evaluate the password
            if (note!!.password.equals(AppUtils.generateHash(editPwd!!.text.toString()))) {
                NavigatorUtils.redirectToViewNoteScreen(this, note!!)

            } else
                AppUtils.showMessage(applicationContext, getString(R.string.error_pwd))
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val DRAWABLE_RIGHT = 2

        if (event.action == MotionEvent.ACTION_UP) {
            if (view.id == R.id.edit_pwd && event.rawX >= editPwd!!.right - editPwd!!.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                togglePwd()
                return true
            }
        }
        return false
    }
}

