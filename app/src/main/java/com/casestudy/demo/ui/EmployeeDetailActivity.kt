package com.casestudy.demo.ui

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.casestudy.demo.BR
import com.casestudy.demo.R
import com.casestudy.demo.databinding.ActivityEmployeeDetailBinding
import com.casestudy.demo.databinding.ItemEmployeeListBinding
import com.casestudy.demo.model.EmployeeList
import com.casestudy.demo.network.ApiClient
import com.casestudy.demo.util.appToast
import com.casestudy.demo.util.hide
import com.casestudy.demo.util.isInternet
import com.casestudy.demo.util.show
import com.github.nitrico.lastadapter.LastAdapter
import com.livinglifetechway.k4kotlin_retrofit.RetrofitCallback
import kotlinx.android.synthetic.main.activity_employee_detail.*

class EmployeeDetailActivity : AppCompatActivity() {

    private val employeeList: ObservableArrayList<EmployeeList> = ObservableArrayList()
    lateinit var mBinding: ActivityEmployeeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@EmployeeDetailActivity, R.layout.activity_employee_detail)
        rv_employee.layoutManager = LinearLayoutManager(this@EmployeeDetailActivity) as RecyclerView.LayoutManager?
        setToolbar()
        getEmployeeData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.recyclerview_using_retrofit)
    }

    private fun getEmployeeData() {
        if (!isInternet()) return
        ApiClient.service.getEmployee().enqueue(RetrofitCallback {
            on200Ok { call, response ->
                employeeList.clear()
                val data = response?.body().orEmpty()
                employeeList.addAll(data)
                setAdapter()
            }
            onFailureNotCancelled { call, throwable ->
                appToast(getString(R.string.error_message))
            }
        })
    }

    private fun setAdapter() {
        if (employeeList.isNotEmpty()) {
            rv_employee.show()
            LastAdapter(employeeList, BR.item)
                    .map<EmployeeList, ItemEmployeeListBinding>(R.layout.item_employee_list)
                    .into(rv_employee)
        } else {
            tv_no_employee_found.show()
            rv_employee.hide()
        }
    }
}
