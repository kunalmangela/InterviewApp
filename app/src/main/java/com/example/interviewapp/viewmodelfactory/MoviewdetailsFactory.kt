package com.example.interviewapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interviewapp.viewmodel.MovieDetailsModel

class MoviewdetailsFactory(
    private val name: String
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return MovieDetailsModel(name) as T
    }
}