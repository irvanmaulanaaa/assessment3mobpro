package com.irvanmaulana0013.barangku.network

import com.irvanmaulana0013.barangku.model.Barang
import com.irvanmaulana0013.barangku.model.OpStatus
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "https://barangku-api.zero-dev.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BarangApiService {
    @GET("barangku")
    suspend fun getBarang(): List<Barang>

    @Multipart
    @POST("barangku")
    suspend fun postBarang(
        @Header("Authorization") userId: String,
        @Part("namaBarang") namaBarang: RequestBody,
        @Part("kategori") kategori: RequestBody,
        @Part("jumlah") jumlah: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus
}

object BarangApi {
    val service: BarangApiService by lazy {
        retrofit.create(BarangApiService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }