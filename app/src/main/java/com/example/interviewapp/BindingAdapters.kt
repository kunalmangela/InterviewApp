package com.example.interviewapp

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.interviewapp.adapter.MovieListAdapter
import com.example.interviewapp.model.Movies

/*@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movies>?) {
    val adapter = recyclerView?.adapter as MovieListAdapter
    adapter.submitList(data)
}*/

@BindingAdapter("movieyeartext")
fun bindmoviename(textView: TextView , textstring: String?) {
    textstring?.let {
        textView.text="Released: "+it
    }
}

@BindingAdapter("movietitletext")
fun bindmovieyear(textView: TextView , textstring: String?) {
    textstring?.let {
        textView.text=it
    }
}




@BindingAdapter("moviegenre")
fun bindmoviegenre(textView: TextView , textstring: String?) {
    textstring?.let {
        textView.text="Genre: "+it
    }
}
@BindingAdapter("movieimdb")
fun bindmovieimdb(textView: TextView , textstring: String?) {
    textstring?.let {
        textView.text="IMDB: "+it
    }
}



@BindingAdapter("imagesourceurl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}