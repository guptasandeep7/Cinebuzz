package com.example.cinebuzz.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinebuzz.R
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.SearchHistoryDataItem

class SearchPageAdapter(private val context: Context?, private var Search: ArrayList<MoviesDataItem>) : RecyclerView.Adapter<SearchPageAdapter.HomeViewHolder>() {

    private var mlistner: onItemClickListener? = null
    interface onItemClickListener{

        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.search_rows,parent,false)
        return HomeViewHolder(view,mlistner)
    }
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val movie = Search[position]
        holder.history.text=movie.name
    }
    override fun getItemCount(): Int {
        return Search.size
    }
    class HomeViewHolder(itemView: View,listener: onItemClickListener?) : RecyclerView.ViewHolder(itemView) {

         var history= itemView.findViewById<TextView>(R.id.history)
        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }


    }

}