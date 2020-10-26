package com.example.interviewapp.network

import androidx.lifecycle.LiveData
import com.example.interviewapp.model.MovieDetails
import com.example.interviewapp.model.MovieList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MovieApiService{

    @GET("/")
    suspend fun getMovielist(@QueryMap params:  Map<String, String>): Response<MovieList?>

    @GET("/")
    suspend fun getMoviedetails(@QueryMap params:  Map<String, String>): Response<MovieDetails?>

}

private const val BASE_URL = "https://www.omdbapi.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object MovieApi {
    val retrofitService : MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}