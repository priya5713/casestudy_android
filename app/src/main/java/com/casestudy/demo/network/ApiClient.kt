package com.casestudy.demo.network

import com.casestudy.demo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "http://dummy.restapiexample.com/api/v1/"
    var retrofit: Retrofit? = null

    // add logging interceptor if DEBUG build
    private val client: Retrofit
        get() {
            if (retrofit == null) {
                val builder = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                val client = OkHttpClient.Builder()
                client.connectTimeout(120, TimeUnit.SECONDS)
                client.readTimeout(120, TimeUnit.SECONDS)

                if (BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    client.addInterceptor(loggingInterceptor)
                }

                builder.client(client.build())

                retrofit = builder.build()
            }
            return retrofit!!
        }

    val service: ApiInterface
        get() = client.create(ApiInterface::class.java)

}