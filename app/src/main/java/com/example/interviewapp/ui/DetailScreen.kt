package com.example.interviewapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.interviewapp.R
import com.example.interviewapp.databinding.ActivityDetailScreenBinding
import com.example.interviewapp.viewmodel.MovieDetailsModel
import com.example.interviewapp.viewmodel.Movieapistatus
import com.example.interviewapp.viewmodel.Moviedetailsapistatus
import com.example.interviewapp.viewmodelfactory.MoviewdetailsFactory

class DetailScreen : AppCompatActivity() {
    //This is main screen binding for  bind views with code directly without using findviewbyid
    lateinit var binding: ActivityDetailScreenBinding

    //This is main screen viewmodel
    lateinit var moviedetailsmodel: MovieDetailsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_detail_screen)

        //get movie id from previous activity through bundles
        val bundle: Bundle? = intent.extras
        bundle?.let {
            val movieid: String = it.getString("movieid").toString()
            moviedetailsmodel= ViewModelProvider(this, MoviewdetailsFactory(movieid)).get(MovieDetailsModel::class.java)
        }

        //bind viewmodel to binding
        binding.moviedetailmodel=moviedetailsmodel
        binding.setLifecycleOwner(this)


        //observer in case we want to do any operation on this event
        moviedetailsmodel.moviedetailsresponse.observe(this, Observer {

        })

        //Change states of api call status and show on screen using images
        moviedetailsmodel.statusdetailscreen.observe(this, Observer {

            it?.let {
                when(it) {
                    Moviedetailsapistatus.LOADING -> {
                        binding.statusImageDetailsScreen.visibility = View.VISIBLE
                        binding.statusImageDetailsScreen.setImageResource(R.drawable.loading_animation)
                    }
                    Moviedetailsapistatus.ERROR -> {

                        binding.statusImageDetailsScreen.visibility = View.VISIBLE
                        binding.statusImageDetailsScreen.setImageResource(R.drawable.ic_connection_error)
                    }
                    Moviedetailsapistatus.DONE -> {

                        binding.statusImageDetailsScreen.visibility = View.GONE
                    }
                }
            }
        })

    }
}