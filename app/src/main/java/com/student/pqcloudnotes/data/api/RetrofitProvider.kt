package com.student.pqcloudnotes.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private const val BASE_URL = "http://10.0.2.2:4000/"

    fun create(): PQCloudNotesApi {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PQCloudNotesApi::class.java)
    }
}
