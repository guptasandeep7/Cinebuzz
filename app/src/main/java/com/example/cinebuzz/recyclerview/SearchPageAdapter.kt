package com.example.cinebuzz.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.dashboard.PlayMovie
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.SearchHistoryDataItem

class SearchPageAdapter(private val context: Context?, private var Search: ArrayList<MoviesDataItem>) : RecyclerView.Adapter<SearchPageAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.search_rows,parent,false)
        return HomeViewHolder(view)
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val movie = Search[position]
        holder.history.text=movie.name

        holder.history.setOnClickListener{
            val intent = Intent(context,PlayMovie::class.java)
            intent.putExtra("MOVIEID",movie._id.toString())
            context?.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {
        return Search.size
    }
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var history= itemView.findViewById<TextView>(R.id.history)

        }


    }

