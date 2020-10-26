package com.example.interviewapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.interviewapp.model.MovieDetails
import com.example.interviewapp.model.Movies
import com.example.interviewapp.network.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {



    //for getting list of movies from api call
    var movieresponsedata= MutableLiveData<List<Movies?>>()

    //for getting details of movies from api call
    var moviedetailsresponsedata= MutableLiveData<MovieDetails?>()





    suspend fun getMovielist(){
        withContext(Dispatchers.Main){
            val params: MutableMap<String, String> = HashMap()
            params["s"] = "star wars"
            params["apikey"] = "32bce2c9"
            val movielistresponse= MovieApi.retrofitService.getMovielist(params)
            movielistresponse.body()?.run {
                movieresponsedata.value=Search
            }

        }
    }


    suspend fun getMovieDetails(movieid: String) {
        withContext(Dispatchers.Main){
            val params: MutableMap<String, String> = HashMap()
            params["i"] = movieid
            params["apikey"] = "32bce2c9"
            val movielistresponse= MovieApi.retrofitService.getMoviedetails(params)
                moviedetailsresponsedata.value=movielistresponse.body()


        }
    }
}