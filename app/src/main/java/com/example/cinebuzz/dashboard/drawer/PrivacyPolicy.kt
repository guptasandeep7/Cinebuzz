package com.example.cinebuzz.dashboard.drawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.cinebuzz.R

class PrivacyPolicy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privacy_policy)
        val toolbar: Toolbar = findViewById(R.id.toolbar5)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}