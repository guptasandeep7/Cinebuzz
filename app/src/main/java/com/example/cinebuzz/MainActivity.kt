package com.example.cinebuzz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.cinebuzz.auth.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, LoginFragment())
        fragmentTransaction.commit()

    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount==0){
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Exit")
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton(R.string.exit) { dialog, id ->
                    finish()
                }
                .setNeutralButton(R.string.cancel) { dialog, id -> }
            val exit = builder.create()
            exit.show()
        }
        else{
            super.onBackPressed()
        }
    }
}