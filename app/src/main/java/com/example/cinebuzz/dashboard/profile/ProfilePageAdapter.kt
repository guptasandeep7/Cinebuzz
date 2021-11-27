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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.dashboard.PlayMovie
import com.example.cinebuzz.dashboard.profile.WishlistFragment.Companion.none2
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder2
import com.example.cinebuzz.retrofit.WishlistDataItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePageAdapter(private val context: Context?,private val wishlist: ArrayList<MoviesDataItem>, private val type: Int) :
    RecyclerView.Adapter<ProfilePageAdapter.ProfileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

        val item = wishlist[position]
        if (type == 1) {
            holder.wishlistBtn.visibility = View.VISIBLE
            holder.line.visibility = View.VISIBLE

//            if (holder.historyRecylcer==null)
//            {
//                holder.none2.text="No Movie"
//                holder.none2.visibility=View.VISIBLE
//            }
        }
        else {
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
            intent.putExtra("MOVIEID", wishlist[position]._id.toString())
            context?.startActivity(intent)
        }

        holder.wishlistBtn.setOnClickListener {
            holder.wishlistBtn.isEnabled = false
            val request2 = ServiceBuilder2.buildService()
            val call2 = request2.wishlistToggle(
                WishlistDataItem(
                    Movieid = item._id,
                    userid = SplashScreen.USERID
                )
            )
            call2.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    if (response.code() == 301) {
                            Toast.makeText(context, "movie removed", Toast.LENGTH_SHORT).show()
                        wishlist.removeAt(holder.bindingAdapterPosition)
                        notifyItemRemoved(holder.bindingAdapterPosition)
                        if(wishlist.isEmpty()){
                            none2.text="NoWishlist"
                            none2.visibility=View.VISIBLE
                        }
                        } else {
                            Toast.makeText(
                                context,
                                response.code().toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(context, "failed ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })

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
        val cardView=itemView.findViewById<ImageView>(R.id.profile_movie_image)
       // val none2 = itemView.findViewById<TextView>(R.id.none2)
        //val none = itemView.findViewById<TextView>(R.id.none)
       // val historyRecylcer = itemView.findViewById<RecyclerView>(R.id.history_recyclerview)
    }

}