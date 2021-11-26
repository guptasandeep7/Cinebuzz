package com.example.cinebuzz.dashboard.drawer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen
import com.example.cinebuzz.model.SomthingWentWrong
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Feedback : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedback)
        val toolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val feed = findViewById<TextInputEditText>(R.id.Feedback)
        val submit = findViewById<Button>(R.id.submit_feedback)

        submit.setOnClickListener {
            if (feed.text.toString().isNotEmpty()) {
                val request = ServiceBuilder.buildService()
                val call = request.feedback(
                    MyDataItem(
                        email = SplashScreen.USEREMAIL,
                        feed = feed.text.toString()
                    )
                )
                call.enqueue(object : Callback<String?> {
                    override fun onResponse(
                        call: Call<String?>,
                        response: Response<String?>
                    ) {
                        if (response.isSuccessful) {

                            Toast.makeText(
                                this@Feedback,
                                "Thanks For Your Feedback",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            startActivity(Intent(this@Feedback, DashboardActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@Feedback, response.code().toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    override fun onFailure(call: Call<String?>, t: Throwable) {

                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.feedb, SomthingWentWrong())
                        transaction.commit()
                        submit.visibility=View.GONE
                    }
                })
            }
            else  feed.error = "Please write feedback"
        }
    }
}