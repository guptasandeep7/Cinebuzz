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
import com.example.cinebuzz.retrofit.SearchHistoryDataItem
import com.squareup.picasso.Picasso

class SearchPageAdapter(private val context: Context?, private var History: List<SearchHistoryDataItem>) : RecyclerView.Adapter<SearchPageAdapter.HomeViewHolder>() {

     val movies : List<SearchHistoryDataItem> = History

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.search_rows,parent,false)
        return HomeViewHolder(view)
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

//       val picture=History[position]
//        holder.history.text=picture.history
    }
    override fun getItemCount(): Int {
        return 12
    }
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var history= itemView.findViewById<TextView>(R.id.history)



    }

}