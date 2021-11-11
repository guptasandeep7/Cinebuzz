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

class HomePageAdapter(private val context: Context? ,private var HomePageMovies: List<MoviesDataItem>) : RecyclerView.Adapter<HomePageAdapter.HomeViewHolder>() {

//    private var images = intArrayOf(R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background)
     val movies : List<MoviesDataItem> = HomePageMovies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.home_rows,parent,false)
        return HomeViewHolder(view)
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

       val picture=HomePageMovies[position]
        holder.movieName.text=picture._id
//        holder.movieImage.setImageResource(images[position])
        Toast.makeText(context, " $position", Toast.LENGTH_SHORT).show()
        holder.bind(movies[position])


    }
    override fun getItemCount(): Int {
        return 5
    }
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var movieImage= itemView.findViewById<ImageView>(R.id.movieImage)
        var movieName =itemView.findViewById<TextView>(R.id.movieName)

        fun bind(data:MoviesDataItem)
        {
         val Url=data.url

            Picasso.get().load(Url).into(movieImage)
        }
    }

}