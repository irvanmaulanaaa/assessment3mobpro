package com.irvanmaulana0013.barangku.network

import com.irvanmaulana0013.barangku.model.Barang
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL = "https://fd23-160-19-227-86.ngrok-free.app/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BarangApiService {
    @GET("barangku")
    suspend fun getBarang(
        @Header("Authorization") userId: String
    ): List<Barang>
}

object BarangApi {
    val service: BarangApiService by lazy {
        retrofit.create(BarangApiService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS}