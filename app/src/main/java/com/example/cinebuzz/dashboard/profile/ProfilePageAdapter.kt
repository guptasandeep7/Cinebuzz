package com.example.cinebuzz.dashboard.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.dashboard.PlayMovie
import com.example.cinebuzz.retrofit.MoviesDataItem

class ProfilePageAdapter(private val context: Context?,private val wishlist: ArrayList<MoviesDataItem>, private val type: Int) :
    RecyclerView.Adapter<ProfilePageAdapter.ProfileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

        val item = wishlist[position]

        if (type == 1) {
            Toast.makeText(context,"$position",Toast.LENGTH_SHORT).show()
            holder.wishlistBtn.visibility = View.VISIBLE
            holder.line.visibility = View.VISIBLE
        }
        else {
            Toast.makeText(context,"$position",Toast.LENGTH_SHORT).show()
            holder.wishlistBtn.visibility = View.GONE
            holder.line.visibility = View.GONE
        }

        holder.movieName.text = item.name
        holder.movieImage.load(BASEURL+item.poster) {
            crossfade(true)
            placeholder(R.drawable.randomise_icon)
        }
        holder.cardView.setOnClickListener{
            val intent = Intent(context, PlayMovie::class.java)
            intent.putExtra("MOVIEID", item._id.toString())
            context?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return wishlist.size
    }
    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val movieImage = itemView.findViewById<ImageView>(R.id.profile_movie_image)
        val movieName = itemView.findViewById<TextView>(R.id.profile_movie_name)
        val wishlistBtn = itemView.findViewById<ImageButton>(R.id.wishlist_btn)
        val line = itemView.findViewById<View>(R.id.profile_line)
        val cardView=itemView.findViewById<CardView>(R.id.profileCardView)
    }

}