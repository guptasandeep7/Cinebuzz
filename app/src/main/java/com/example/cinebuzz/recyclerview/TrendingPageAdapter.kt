package com.example.cinebuzz.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.squareup.picasso.Picasso

class TrendingPageAdapter(private val context: Context?, private var HomePageMovies: List<MoviesDataItem>) : RecyclerView.Adapter<TrendingPageAdapter.HomeViewHolder>() {

//    private var images = intArrayOf(R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background)
     val movies : List<MoviesDataItem> = HomePageMovies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.movies,parent,false)
        return HomeViewHolder(view)
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

       val picture=HomePageMovies[position]
        holder.movieName.text=picture.name
//        holder.movieImage.setImageResource(images[position])
        holder.bind(movies[position])
    }
    override fun getItemCount(): Int {
        return 12
    }
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var movieImage= itemView.findViewById<ImageView>(R.id.movieImage2)
        var movieName =itemView.findViewById<TextView>(R.id.movieName2)

        fun bind(data:MoviesDataItem)
        {
         val Url=data.posterurl

            Picasso.get().load(Url).into(movieImage)
        }
    }

}