package com.example.meuapp.utils

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Retrofit2Api {

    companion object {
        private val URL_BASE = "http://10.0.0.121:9003/"
        //private val URL_BASE = "https://fe2acef7-3e1d-4612-9536-11d1fd219f60.mock.pstmn.io/"
        private val DEFAULT_TIMEOUT = 240L

        fun getBuilder(): ApiInterface {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            httpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            httpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            httpClient.callTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            httpClient.addInterceptor(logging) // <-- this is the important line!

            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiInterface::class.java)
        }
    }
}