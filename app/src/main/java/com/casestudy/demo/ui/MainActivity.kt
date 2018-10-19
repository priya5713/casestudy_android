package com.casestudy.demo.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.casestudy.demo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_recycler_view.setOnClickListener {
            startActivity(Intent(this@MainActivity, EmployeeDetailActivity::class.java))
        }
        button_room_example.setOnClickListener {
            startActivity(Intent(this@MainActivity, NotesListActivity::class.java))
            finish()
        }
        button_recycler_view_mvp.setOnClickListener {
            startActivity(Intent(this@MainActivity, DogsBreedsActivity::class.java))
        }
    }
}
