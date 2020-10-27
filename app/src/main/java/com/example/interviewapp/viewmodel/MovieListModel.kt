package com.example.interviewapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.interviewapp.model.Movies
import com.example.interviewapp.repository.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

enum class Movieapistatus { LOADING, ERROR, DONE }

class MovieListModel : ViewModel(){

    //creater repository object where we called retrofit network api calling method
    val repository= MovieRepository()

    //movie list adat we gob from repository after successful api call
    val movielistresponse: LiveData<List<Movies?>> = repository.movieresponsedata


    //here we create status variable with  its backing property to handle network call result states
    private val _status = MutableLiveData<Movieapistatus>()
    val status: LiveData<Movieapistatus>
        get() = _status



    init {
        getMovielistdata()
    }


    //from here we started api call method from repository
    private fun getMovielistdata() {
        launchDataLoad {
            //here api call
            repository.getMovielist()
        }
    }


    private fun launchDataLoad(block: suspend () -> Unit): Job {
        //coroutin launched here
        return viewModelScope.launch {
            _status.value=Movieapistatus.LOADING
            try {
                //successful api call
                block()
                _status.value=Movieapistatus.DONE
            } catch (error: Exception) {
                //error occured during api call
                _status.value=Movieapistatus.ERROR
            } finally {

            }
        }
    }







}