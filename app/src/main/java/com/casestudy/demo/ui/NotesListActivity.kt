package com.casestudy.demo.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.TextView
import com.casestudy.demo.R
import com.casestudy.demo.Repository.NoteRepository
import com.casestudy.demo.adapter.NotesListAdapter
import com.casestudy.demo.model.Note
import com.casestudy.demo.util.AppConstants
import com.casestudy.demo.util.AppConstants.Companion.ACTIVITY_REQUEST_CODE
import com.casestudy.demo.util.AppConstants.Companion.INTENT_DELETE
import com.casestudy.demo.util.AppConstants.Companion.INTENT_DESC
import com.casestudy.demo.util.AppConstants.Companion.INTENT_ENCRYPT
import com.casestudy.demo.util.AppConstants.Companion.INTENT_PWD
import com.casestudy.demo.util.AppConstants.Companion.INTENT_TASK
import com.casestudy.demo.util.AppConstants.Companion.INTENT_TITLE
import com.casestudy.demo.util.NavigatorUtils
import com.casestudy.demo.util.RecyclerItemClickListener


class NotesListActivity : AppCompatActivity(), View.OnClickListener, RecyclerItemClickListener.OnRecyclerViewItemClickListener, AppConstants {
    private var emptyView: TextView? = null
    private var recyclerView: RecyclerView? = null
    private var notesListAdapter: NotesListAdapter? = null
    private var floatingActionButton: FloatingActionButton? = null

    private var noteRepository: NoteRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        noteRepository = NoteRepository(applicationContext)

        recyclerView = findViewById(R.id.task_list)
        recyclerView!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView!!.addOnItemTouchListener(RecyclerItemClickListener(this, this))

        floatingActionButton = findViewById(R.id.fab)
        floatingActionButton!!.setOnClickListener(this)

        emptyView = findViewById(R.id.empty_view)

        updateTaskList()
    }


    private fun updateTaskList() {
        noteRepository?.tasks?.observe(this, Observer<List<Note>> { notes ->
            if (notes!!.size > 0) {
                emptyView!!.visibility = View.GONE
                recyclerView!!.visibility = View.VISIBLE
                if (notesListAdapter == null) {
                    notesListAdapter = NotesListAdapter(notes as MutableList<Note>)
                    recyclerView!!.adapter = notesListAdapter

                } else
                    notesListAdapter!!.addTasks(notes)
            } else
                updateEmptyView()
        })
    }

    private fun updateEmptyView() {
        emptyView!!.visibility = View.VISIBLE
        recyclerView!!.visibility = View.GONE
    }


    /*
     * New note to be added
     * */
    override fun onClick(view: View) {
        val intent = Intent(this@NotesListActivity, AddNoteActivity::class.java)
        startActivityForResult(intent, ACTIVITY_REQUEST_CODE)
    }


    /*
     * update/delete existing note
     * */
    override fun onItemClick(parentView: View, childView: View, position: Int) {
        val note = notesListAdapter!!.getItem(position)
        if (note.isEncrypt) {
            NavigatorUtils.redirectToPwdScreen(this, note)

        } else {
            NavigatorUtils.redirectToEditTaskScreen(this, note)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (data!!.hasExtra(INTENT_TASK)) {
                if (data.hasExtra(INTENT_DELETE)) {
                    noteRepository!!.deleteTask(data.getSerializableExtra(INTENT_TASK) as Note)

                } else {
                    noteRepository!!.updateTask(data.getSerializableExtra(INTENT_TASK) as Note)
                }
            } else {
                val title = data.getStringExtra(INTENT_TITLE)
                val desc = data.getStringExtra(INTENT_DESC)
                val pwd = data.getStringExtra(INTENT_PWD)
                val encrypt = data.getBooleanExtra(INTENT_ENCRYPT, false)
                noteRepository!!.insertTask(title, desc, encrypt, pwd)
            }
            updateTaskList()
        }
    }
}

