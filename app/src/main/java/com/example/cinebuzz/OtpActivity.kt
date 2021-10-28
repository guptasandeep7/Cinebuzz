package com.example.cinebuzz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OtpActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        val verifyBtn = findViewById<Button>(R.id.verify_btn)

        verifyBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,PasswordActivity::class.java))
        })
    }
}