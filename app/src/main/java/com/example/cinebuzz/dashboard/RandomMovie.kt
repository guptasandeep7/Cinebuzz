package com.example.cinebuzz.dashboard

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.cinebuzz.R

class RandomMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.random_movie)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this,android.R.color.black)))


        val category = intent.getStringExtra("Category")

    }

}