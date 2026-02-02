package com.student.pqcloudnotes.network

import com.student.pqcloudnotes.data.AppConfigStore
import okhttp3.Interceptor

object RiskHeaderProvider {
    fun create(): Interceptor? {
        return Interceptor { chain ->
            val risk = AppConfigStore.deviceRisk
            val request = chain.request().newBuilder()
                .addHeader("X-Device-Risk", risk)
                .build()
            chain.proceed(request)
        }
    }
}
