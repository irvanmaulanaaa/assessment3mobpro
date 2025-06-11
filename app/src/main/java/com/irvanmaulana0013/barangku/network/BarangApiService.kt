package com.irvanmaulana0013.barangku.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL = "https://fd23-160-19-227-86.ngrok-free.app/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface BarangApiService {
    @GET("barangku")
    suspend fun getBarang(
        @Header("Authorization") userId: String
    ): String
}

object BarangApi {
    val service: BarangApiService by lazy {
        retrofit.create(BarangApiService::class.java)
    }
}