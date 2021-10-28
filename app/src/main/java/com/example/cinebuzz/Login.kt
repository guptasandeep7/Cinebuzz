package com.example.cinebuzz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val forget=findViewById<TextView>(R.id.textView2)
        forget.setOnClickListener{
            val intent= Intent(this,email_verify::class.java)
            startActivity(intent)
        }

    }
}