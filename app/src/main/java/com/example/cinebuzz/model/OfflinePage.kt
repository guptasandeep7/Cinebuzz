package com.example.cinebuzz.model

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinebuzz.ConnectionCheck
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen

class OfflinePage : AppCompatActivity() {
    private lateinit var cld : ConnectionCheck
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.offline_page)

    checkNetworkConnection()

}

private fun checkNetworkConnection() {
    cld = ConnectionCheck(application)

    cld.observe(this, { isConnected ->

        if (isConnected){

            val intent = Intent(this, SplashScreen::class.java)
            finish()
            startActivity(intent)
        }

    })
}
}
