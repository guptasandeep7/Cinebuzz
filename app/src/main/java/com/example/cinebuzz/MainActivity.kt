package com.example.cinebuzz
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinebuzz.auth.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, LoginFragment())
        fragmentTransaction.commit()

    }

    }
