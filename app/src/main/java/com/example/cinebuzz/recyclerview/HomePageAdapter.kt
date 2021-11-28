package com.example.cinebuzz.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen
import com.example.cinebuzz.retrofit.MoviesDataItem

class HomePageAdapter(
    private val context: Context?,
    private var HomePageMovies: List<MoviesDataItem>
) : RecyclerView.Adapter<HomePageAdapter.HomeViewHolder>() {
    private var mlistner: onItemClickListener? = null
    val movies: List<MoviesDataItem> = HomePageMovies

    interface onItemClickListener {

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistner = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_rows, parent, false)
        return HomeViewHolder(view, mlistner)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        val picture = HomePageMovies[position]
        holder.movieName.text = picture.name
        holder.movieImage.load(SplashScreen.BASEURL+picture.poster) {
            crossfade(true)
            placeholder(R.drawable.ic_vector__11_)
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    class HomeViewHolder(itemView: View, listener: onItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {

        var movieImage = itemView.findViewById<ImageView>(R.id.movieImage)
        var movieName = itemView.findViewById<TextView>(R.id.movieName)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

}