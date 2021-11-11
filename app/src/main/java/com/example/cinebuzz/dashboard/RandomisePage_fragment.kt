package com.example.cinebuzz.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.cinebuzz.R

class RandomisePage_fragment :Fragment(), View.OnClickListener{
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
         val view = inflater.inflate(R.layout.randomise_page,container,false)

        view.findViewById<CardView>(R.id.action).setOnClickListener(this)
        view.findViewById<CardView>(R.id.drama).setOnClickListener(this)
        view.findViewById<CardView>(R.id.comedy).setOnClickListener(this)
        view.findViewById<CardView>(R.id.horror).setOnClickListener(this)
        view.findViewById<CardView>(R.id.scifi).setOnClickListener(this)
        view.findViewById<CardView>(R.id.other).setOnClickListener(this)

    return view
}
    fun intent(category:String){
        val intent = Intent(context,RandomMovie::class.java)
        intent.putExtra("Category",category)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.action->intent("action")
            R.id.drama->intent("drama")
            R.id.comedy->intent("comedy")
            R.id.horror->intent("horror")
            R.id.scifi->intent("scifi")
            R.id.other->intent("other")
        }
    }
}