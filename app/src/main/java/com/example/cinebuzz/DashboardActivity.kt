package com.example.cinebuzz

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cinebuzz.SplashScreen.Companion.logInState
import com.example.cinebuzz.dashboard.drawer.AboutUs
import com.example.cinebuzz.dashboard.drawer.ChangePassword
import com.example.cinebuzz.dashboard.drawer.Feedback
import com.example.cinebuzz.dashboard.drawer.PrivacyPolicy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {


    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController
    lateinit var toggle: ActionBarDrawerToggle



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val hearderView=navView.getHeaderView(0)
        val drawerName=hearderView.findViewById<TextView>(R.id.drawerName)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {

            drawerName.text=SplashScreen.USERNAME
            when (it.itemId) {
                R.id.about_us -> {
                    val intent= Intent(this, AboutUs::class.java)
                    startActivity(intent)
                }
                R.id.privacy_policy -> {
                    val intent= Intent(this, PrivacyPolicy::class.java)
                    startActivity(intent)
                }
                R.id.feedback -> {
                    val intent= Intent(this, Feedback::class.java)
                    startActivity(intent)
                }
                R.id.change_password ->{
                    val intent= Intent(this, ChangePassword::class.java)
                    startActivity(intent)
                }
                R.id.signout ->{
                    lifecycleScope.launch {  logInState(false) }
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }

            }
            true
        }

        bottomNav = findViewById(R.id.bottomNav)
        navController = findNavController(R.id.fragmentContainerView2)
        bottomNav.setupWithNavController(navController)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

}