package com.example.freshauctionapp.network

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object NetworkModule {
    private val keyValue = ""
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
    }

    fun makeHttpUrl(scode: String, date: String, amount: String): HttpUrl{
        val result = HttpUrl.Builder()
            .scheme("http")
            .host("apis.data.go.kr")
            .addPathSegment("B552895")
            .build()
        return result
    }
}