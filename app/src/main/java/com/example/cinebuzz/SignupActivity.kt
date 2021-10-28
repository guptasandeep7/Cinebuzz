package com.example.cinebuzz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val verifyEmailBtn = findViewById<Button>(R.id.verifyEmail_btn)

        verifyEmailBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,OtpActivity::class.java))
        })
    }
}