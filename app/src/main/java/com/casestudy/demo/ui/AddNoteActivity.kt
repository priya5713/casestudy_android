package com.casestudy.demo.ui

import android.app.Activity
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import com.casestudy.demo.R
import com.casestudy.demo.databinding.ActivityAddNoteBinding
import com.casestudy.demo.model.Note
import com.casestudy.demo.util.AppConstants
import com.casestudy.demo.util.AppConstants.Companion.INTENT_DELETE
import com.casestudy.demo.util.AppConstants.Companion.INTENT_DESC
import com.casestudy.demo.util.AppConstants.Companion.INTENT_ENCRYPT
import com.casestudy.demo.util.AppConstants.Companion.INTENT_PWD
import com.casestudy.demo.util.AppConstants.Companion.INTENT_TASK
import com.casestudy.demo.util.AppConstants.Companion.INTENT_TITLE
import com.casestudy.demo.util.AppUtils

class AddNoteActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener, View.OnTouchListener, AppConstants {

    lateinit var mBinding: ActivityAddNoteBinding

    private var note: Note? = null
    private var pwdVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@AddNoteActivity, R.layout.activity_add_note)

        mBinding.editPwd.setOnTouchListener(this)
        mBinding.checkbox.setOnCheckedChangeListener(this)
        mBinding.toolbar.btnClose?.setOnClickListener(this)
        mBinding.toolbar.btnDone?.setOnClickListener(this)

        note = intent.getSerializableExtra(INTENT_TASK) as? Note

        if (note == null) {
            mBinding.toolbar.title?.text = getString(R.string.add_task_title)
            mBinding.toolbar.btnClose?.setImageResource(R.drawable.btn_done)
            mBinding.toolbar.btnClose?.tag = R.drawable.btn_done
            mBinding.textTime.text = AppUtils.getFormattedDateString(AppUtils.currentDateTime)

        } else {
            mBinding.toolbar.title?.text = getString(R.string.edit_task_title)
            mBinding.toolbar.btnClose?.setImageResource(R.drawable.ic_delete)
            mBinding.toolbar.btnClose?.tag = R.drawable.ic_delete
            if (note?.title != null && !note?.title!!.isEmpty()) {
                mBinding.editTitle.setText(note?.title)
                mBinding.editTitle.setSelection(mBinding.editTitle.text.length)
            }
            if (note?.description != null && !note?.description!!.isEmpty()) {
                mBinding.editDesc.setText(note?.description)
                mBinding.editDesc.setSelection(mBinding.editDesc.text.length)
            }
            if (note?.createdAt != null) {
                mBinding.textTime.text = note?.createdAt?.let { AppUtils.getFormattedDateString(it) }
            }
            if (note?.password != null && !note?.password!!.isEmpty()) {
                mBinding.editPwd.setText(note?.password)
                mBinding.editPwd.setSelection(mBinding.editPwd.text.length)
            }
            mBinding.checkbox.isChecked = note!!.isEncrypt
        }
        AppUtils.openKeyboard(applicationContext)
    }


    private fun togglePwd() {
        if (!pwdVisible) {
            pwdVisible = java.lang.Boolean.TRUE
            mBinding.editPwd.transformationMethod = null
            val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_pwd)!!.mutate()
            drawable.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(applicationContext, R.color.line), PorterDuff.Mode.MULTIPLY)
            mBinding.editPwd.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

        } else {
            pwdVisible = java.lang.Boolean.FALSE
            mBinding.editPwd.transformationMethod = PasswordTransformationMethod()
            val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_pwd)!!.mutate()
            drawable.colorFilter = PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)
            mBinding.editPwd.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        }
        mBinding.editPwd.setSelection(mBinding.editPwd.length())
    }


    override fun onCheckedChanged(compoundButton: CompoundButton, b: Boolean) {
        if (b) {
            mBinding.editPwd.visibility = View.VISIBLE
            mBinding.editPwd.isFocusable = true
        } else {
            mBinding.editPwd.visibility = View.INVISIBLE
        }
    }

    override fun onClick(view: View) {
        AppUtils.hideKeyboard(this)
        if (view === mBinding.toolbar?.btnClose) {

            if (mBinding.toolbar?.btnClose?.tag as Int === R.drawable.btn_done) {
                setResult(Activity.RESULT_CANCELED)

            } else {
                val intent = intent
                intent.putExtra(INTENT_DELETE, true)
                intent.putExtra(INTENT_TASK, note)
                setResult(Activity.RESULT_OK, intent)
            }

            finish()
            overridePendingTransition(R.anim.stay, R.anim.slide_down)

        } else if (view === mBinding.toolbar?.btnDone) {
            val intent = intent
            if (note != null) {
                note?.title = mBinding.editTitle.text.toString()
                note?.description = mBinding.editDesc.text.toString()
                note?.isEncrypt = mBinding.checkbox.isChecked
                note?.password = mBinding.editPwd.text.toString()
                intent.putExtra(INTENT_TASK, note)

            } else {
                intent.putExtra(INTENT_TITLE, mBinding.editTitle.text.toString())
                intent.putExtra(INTENT_DESC, mBinding.editDesc.text.toString())
                intent.putExtra(INTENT_ENCRYPT, mBinding.checkbox.isChecked)
                intent.putExtra(INTENT_PWD, mBinding.editPwd.text.toString())
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
            overridePendingTransition(R.anim.stay, R.anim.slide_down)
        }
    }


    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val DRAWABLE_RIGHT = 2

        if (event.action == MotionEvent.ACTION_UP) {
            if (view.id == R.id.edit_pwd && event.rawX >= mBinding.editPwd.right - mBinding.editPwd.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                togglePwd()
                return true
            }
        }
        return false
    }
}

