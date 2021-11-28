package com.example.cinebuzz.model

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cinebuzz.*
import kotlinx.coroutines.launch

class OfflinePage : AppCompatActivity() {
    private lateinit var cld: ConnectionCheck
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.offline_page)
        val tryagain = findViewById<Button>(R.id.offlineButton)
        checkNetworkConnection()
        tryagain.setOnClickListener {
            cld.observe(this, { isConnected ->
                if (isConnected) {
                    lifecycleScope.launch {
                        if (SplashScreen.isLogedIn() == true) {
                            val intent = Intent(this@OfflinePage, DashboardActivity::class.java)
                            startActivity(intent)
                            onStop()
                            finish()
                        } else {
                            val intent = Intent(this@OfflinePage, MainActivity::class.java)
                            startActivity(intent)
                            onStop()
                            finish()
                        }
                    }
                }

            })
        }

    }

    private fun checkNetworkConnection() {
        cld = ConnectionCheck(application)

        cld.observe(this, { isConnected ->

            if (isConnected) {
                lifecycleScope.launch {
                    if (SplashScreen.isLogedIn() == true) {
                        val intent = Intent(this@OfflinePage, DashboardActivity::class.java)
                        startActivity(intent)
                        onStop()
                        finish()
                    } else {
                        val intent = Intent(this@OfflinePage, MainActivity::class.java)
                        startActivity(intent)
                        onStop()
                        finish()
                    }
                }

            }

        })
    }
}
