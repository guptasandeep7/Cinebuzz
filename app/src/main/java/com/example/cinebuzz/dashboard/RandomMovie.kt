package com.example.cinebuzz.dashboard

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.retrofit.MoviesDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomMovie : AppCompatActivity() {

    lateinit var movieImage: ImageView
    lateinit var movieName: TextView
    lateinit var nextBtn: Button
    lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.random_movie)

        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    android.R.color.black
                )
            )
        )

        movieImage = findViewById(R.id.movie_image)
        movieName = findViewById(R.id.movie_name)
        nextBtn = findViewById(R.id.button)
        category = intent.getStringExtra("Category")!!

        findMovie()

        nextBtn.setOnClickListener(View.OnClickListener {
            findMovie()
        })

    }

    fun findMovie() {
        val request1 = ServiceBuilder.buildService()
        val call1 = request1.random(MoviesDataItem(genre = category))
        call1.enqueue(object : Callback<MoviesDataItem?> {
            override fun onResponse(
                call: Call<MoviesDataItem?>,
                response: Response<MoviesDataItem?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    movieImage.load(responseBody.posterurl) {
                        placeholder(R.drawable.randomise_icon)
                        crossfade(true)
                    }
                    movieName.text = responseBody.name
                } else {
                    Toast.makeText(applicationContext, "No movie found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MoviesDataItem?>, t: Throwable) {
                Toast.makeText(applicationContext, "failed ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}