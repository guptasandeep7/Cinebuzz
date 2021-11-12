package com.example.cinebuzz.dashboard.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.retrofit.MoviesDataItem

class ProfilePageAdapter(private var wishlist: List<MoviesDataItem>, private val type: Int) :
    RecyclerView.Adapter<ProfilePageAdapter.ProfileViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {


        if (type == 1) {
            holder.wishlistBtn.visibility = View.VISIBLE
            holder.line.visibility = View.VISIBLE
        } else {
            holder.wishlistBtn.visibility = View.GONE
            holder.line.visibility = View.GONE
        }

        val item = wishlist[position]
        holder.movieName.text = item.name
        holder.movieImage.load(item.posterurl) {
            crossfade(true)
            placeholder(R.drawable.randomise_icon)
        }


    }

    override fun getItemCount(): Int {
        return wishlist.size
    }

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var movieImage = itemView.findViewById<ImageView>(R.id.movie_image)
        var movieName = itemView.findViewById<TextView>(R.id.movie_name)
        var wishlistBtn = itemView.findViewById<ImageButton>(R.id.wishlist_btn)
        var line = itemView.findViewById<View>(R.id.line)


    }

}