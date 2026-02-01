package com.student.pqcloudnotes.data.api

import com.student.pqcloudnotes.data.auth.TokenStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private const val BASE_URL = "http://10.0.2.2:4000/"

    fun create(tokenStore: TokenStore? = null): PQCloudNotesApi {
        val authInterceptor = Interceptor { chain ->
            val session = tokenStore?.load()
            val request = if (session?.accessToken != null) {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${session.accessToken}")
                    .build()
            } else {
                chain.request()
            }
            chain.proceed(request)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PQCloudNotesApi::class.java)
    }
}
