package com.example.cinebuzz.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.dashboard.profile.ReviewDataItem

class ReviewsAdapter(private var reviews: List<ReviewDataItem>) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {

        val item = reviews[position]
        holder.reviewName.text = item.name
        if (item.dpUrl == "NaN") {
            holder.reviewDp.setImageResource(R.drawable.ic_undraw_profile_pic_ic5t_2)
        } else {
            holder.reviewDp.load(BASEURL + item.dpUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_undraw_profile_pic_ic5t_2)
                transformations(CircleCropTransformation())
                error(R.drawable.ic_undraw_profile_pic_ic5t_2)
            }
        }

        holder.reviewText.text = item.reviewText


    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var reviewDp = itemView.findViewById<ImageView>(R.id.review_dp)
        var reviewName = itemView.findViewById<TextView>(R.id.review_name)
        var reviewText = itemView.findViewById<TextView>(R.id.review_text)


    }


}