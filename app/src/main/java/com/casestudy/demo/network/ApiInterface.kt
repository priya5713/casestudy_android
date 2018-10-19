package com.casestudy.demo.network

import com.casestudy.demo.model.EmployeeList
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("employees")
    fun getEmployee(): Call<List<EmployeeList>>

}