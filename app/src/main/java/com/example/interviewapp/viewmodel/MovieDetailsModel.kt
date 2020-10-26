package com.example.interviewapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewapp.model.MovieDetails
import com.example.interviewapp.model.Movies
import com.example.interviewapp.repository.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

enum class Moviedetailsapistatus { LOADING, ERROR, DONE }

class MovieDetailsModel(val movieid: String) : ViewModel(){

    //creater repository object where we called retrofit network api calling method
    val repository= MovieRepository()

    //movie list adat we gob from repository after successful api call
    val moviedetailsresponse: LiveData<MovieDetails?> = repository.moviedetailsresponsedata

    //here we create status variable with  its backing property to handle network call result states
    private val _statusdetailscreen = MutableLiveData<Moviedetailsapistatus>()
    val statusdetailscreen: LiveData<Moviedetailsapistatus>
        get() = _statusdetailscreen




    init {
        getMoviedetailsdata(movieid)
    }



    //from here we started api call method from repository
    private fun getMoviedetailsdata(movieid: String) {
        launchDataLoad {
            //here api call
            repository.getMovieDetails(movieid)
        }
    }


    private fun launchDataLoad(block: suspend () -> Unit): Job {
        //coroutin launched here
        return viewModelScope.launch {
            _statusdetailscreen.value=Moviedetailsapistatus.LOADING
            try {
                block()
                _statusdetailscreen.value=Moviedetailsapistatus.DONE
            } catch (error: Exception) {
                _statusdetailscreen.value=Moviedetailsapistatus.ERROR
            } finally {

            }
        }
    }




}