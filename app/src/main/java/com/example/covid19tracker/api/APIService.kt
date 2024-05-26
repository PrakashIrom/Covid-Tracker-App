package com.example.covid19tracker.api

import com.example.covid19tracker.model.ApiResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://covid-19-statistics.p.rapidapi.com/"
private const val API_KEY = "51fde119bdmsh91515729804c331p1a6213jsnb2fd8b8c57df"
private const val HOST = "covid-19-statistics.p.rapidapi.com"

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", HOST)
            .build()
        chain.proceed(request)
    }
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

interface APIService {

    @GET("reports")
    suspend fun getReports(
        @Query("city_name") cityName: String = "Mumbai",
        @Query("region_province") regionProvince: String = "Maharashtra",
        @Query("iso") iso: String = "India",
        @Query("region_name") regionName: String = "IND",
        @Query("q") q: String = "India Mumbai",
        @Query("date") date: String = "2020-04-16" //filter out the fetched datas
    ): ApiResponse
}

object CovidApi{
    val retrofitService: APIService by lazy {
        retrofit.create(APIService::class.java)//::class.java syntax is used to obtain the Java
        // Class object corresponding to a Kotlin class or type.
        //ensure that the Kotlin class is appropriately converted to the type expected by the Java-based Retrofit library
    }
}