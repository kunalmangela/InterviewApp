package com.example.interviewapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.interviewapp.R
import com.example.interviewapp.adapter.MovieListAdapter
import com.example.interviewapp.databinding.ActivityMainScreenBinding
import com.example.interviewapp.viewmodel.MovieListModel
import com.example.interviewapp.viewmodel.Movieapistatus

class MainScreen : AppCompatActivity() {
    //This is main screen binding for  bind views with code directly without using findviewbyid
    lateinit var binding: ActivityMainScreenBinding

    //This is main screen viewmodel
    lateinit var movieviewmodel: MovieListModel

    //This is main screen movielist adapter to show list and filter list related function write in
    //this file
    lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding= DataBindingUtil.setContentView(this,R.layout.activity_main_screen)

        movieviewmodel= ViewModelProvider(this).get(MovieListModel::class.java)
        binding.movielistmodel=movieviewmodel
        binding.setLifecycleOwner  (this)


        //Created adapter object and its item click listner
        adapter = MovieListAdapter(MovieListAdapter.MovielistListener { movieid ->
            movieid.let {
                val intent = Intent(this, DetailScreen::class.java)
                intent.putExtra("movieid", movieid)
                startActivity(intent)

            }
        })

        //set adapter to recyclerview of movie list
        binding.movierecyclerview.adapter= adapter

        //Change adapter state after observed live data list of movies data from viewmodel
        movieviewmodel.movielistresponse.observe(this, Observer {
            it?.let {
                if(it.size>0) {
                    adapter.movielist = it
                    adapter.movieFilterList = it
                    adapter.notifyDataSetChanged()
                }else{
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_baseline_hourglass_empty_24)
                }


            }
        })




        //Change states of api call status and show on screen using images
        movieviewmodel.status.observe(this, Observer {
            it?.let {
                when (it) {
                    Movieapistatus.LOADING -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.loading_animation)
                    }
                    Movieapistatus.ERROR -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    }
                    Movieapistatus.DONE -> {
                        binding.statusImage.visibility = View.GONE
                    }
                }
            }
        })


        //filter list using text changed property
        binding.moviefilter.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.getFilter().filter(s.toString().toLowerCase())
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {// TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {}
        })





        //hide keyboard on enter click of keyboard
        binding.moviefilter.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Your piece of code on keyboard search click
                 hideKeyboard(this)

                return@OnEditorActionListener true
            }
            false
        })




    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = activity.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    override fun onPause() {
        super.onPause()
        binding.moviefilter.clearFocus()
    }
}