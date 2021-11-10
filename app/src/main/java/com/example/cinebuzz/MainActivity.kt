package com.example.cinebuzz
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cinebuzz.auth.LoginFragment
import com.example.cinebuzz.dashboard.HomePage_fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragment_container, HomePage_fragment())
//        fragmentTransaction.commit()
        startActivity(Intent(this, DashboardActivity::class.java))

    }
}