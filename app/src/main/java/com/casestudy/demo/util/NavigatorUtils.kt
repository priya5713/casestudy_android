package com.casestudy.demo.util

import android.app.Activity
import android.content.Intent

import com.casestudy.demo.model.Note
import com.casestudy.demo.ui.AddNoteActivity
import com.casestudy.demo.ui.PwdActivity
import com.casestudy.demo.util.AppConstants.Companion.ACTIVITY_REQUEST_CODE
import com.casestudy.demo.util.AppConstants.Companion.INTENT_TASK


class NavigatorUtils : AppConstants {
    companion object {


        fun redirectToPwdScreen(activity: Activity,
                                note: Note) {
            val intent = Intent(activity, PwdActivity::class.java)
            intent.putExtra(INTENT_TASK, note)
            activity.startActivityForResult(intent, ACTIVITY_REQUEST_CODE)
        }


        fun redirectToEditTaskScreen(activity: Activity,
                                     note: Note) {
            val intent = Intent(activity, AddNoteActivity::class.java)
            intent.putExtra(INTENT_TASK, note)
            activity.startActivityForResult(intent, ACTIVITY_REQUEST_CODE)
        }

        fun redirectToViewNoteScreen(activity: Activity,
                                     note: Note) {
            val intent = Intent(activity, AddNoteActivity::class.java)
            intent.putExtra(INTENT_TASK, note)
            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}
