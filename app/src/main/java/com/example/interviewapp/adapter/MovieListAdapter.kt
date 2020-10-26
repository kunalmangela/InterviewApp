package com.example.interviewapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.interviewapp.databinding.MovieListRowsBinding
import com.example.interviewapp.model.Movies

class MovieListAdapter(val clickListener: MovielistListener)  : ListAdapter<Movies, MovieListAdapter.MovieViewHolder>(DiffCallback) {

    //save whole list data with filtering
    var movieFilterList =  listOf<Movies?>()
        set(value) {
            field = value

        }

    //save whole list data without filtering
    var movielist =  listOf<Movies?>()
        set(value) {
            field = value
        }


    class MovieViewHolder(private var binding: MovieListRowsBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movies, clickListener: MovielistListener) {
            binding.movie = movie
            binding.clickListener=clickListener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieViewHolder {
        return MovieViewHolder(MovieListRowsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val marsProperty = movieFilterList[position]
        marsProperty?.let { holder.bind(it,clickListener) }
    }


     fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                if (charSearch.isEmpty()) {
                    movieFilterList = movielist
                } else {
                    val resultList = ArrayList<Movies>()
                    for (row in movielist) {
                       row?.run{
                           if(Title.toLowerCase().contains(charSearch)){
                               resultList.add(row)
                           }
                       }
                    }
                    movieFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = movieFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                    movieFilterList = results?.values as ArrayList<Movies?>
                    notifyDataSetChanged()

            }

        }
    }

    override fun getItemCount(): Int {
        return movieFilterList.size
    }


    class MovielistListener (val clickListener: (movieid: String) -> Unit) {
        fun onClick(movie: Movies) = clickListener(movie.imdbID)
    }
}