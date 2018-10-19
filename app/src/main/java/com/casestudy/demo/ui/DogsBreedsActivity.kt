package com.casestudy.demo.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import com.casestudy.demo.R
import com.casestudy.demo.databinding.ActivityDogBreedsBinding
import com.casestudy.demo.util.isInternet
import com.casestudy.demo.viewmodel.DogBreedsViewModel
import kotlinx.android.synthetic.main.activity_employee_detail.*

class DogsBreedsActivity : AppCompatActivity() {
    private var viewModel: DogBreedsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_breeds)
        setupBindings(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.recyclerview_using_mvp)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        val activityBinding = DataBindingUtil.setContentView<ActivityDogBreedsBinding>(this, R.layout.activity_dog_breeds)
        viewModel = ViewModelProviders.of(this).get(DogBreedsViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel?.init()
        }
        activityBinding.model = viewModel
        setupListUpdate()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupListUpdate() {
        viewModel?.loading?.set(View.VISIBLE)
        if (!isInternet()) return
        viewModel?.fetchList()
        viewModel?.breeds?.observe(this, Observer { dogBreeds ->
            viewModel?.loading?.set(View.GONE)
            if (dogBreeds?.size == 0) {
                viewModel?.showEmpty?.set(View.VISIBLE)
            } else {
                viewModel?.showEmpty?.set(View.GONE)
                viewModel?.setDogBreedsInAdapter(dogBreeds)
            }
        })
        setupListClick()
    }

    private fun setupListClick() {
        viewModel?.getSelected()?.observe(this, Observer { dogBreed ->
            if (dogBreed != null) {
                Toast.makeText(this@DogsBreedsActivity, "You selected a " + dogBreed.breed, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

