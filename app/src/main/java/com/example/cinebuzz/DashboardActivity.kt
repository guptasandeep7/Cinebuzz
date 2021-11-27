package com.example.cinebuzz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
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
import coil.load
import com.example.cinebuzz.SplashScreen.Companion.isLogedIn
import com.example.cinebuzz.SplashScreen.Companion.logInState
import com.example.cinebuzz.dashboard.HomePage_fragment
import com.example.cinebuzz.dashboard.drawer.AboutUs
import com.example.cinebuzz.dashboard.drawer.ChangePassword
import com.example.cinebuzz.dashboard.drawer.Feedback
import com.example.cinebuzz.dashboard.drawer.PrivacyPolicy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
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
        val hearderView = navView.getHeaderView(0)
        val drawerName = hearderView.findViewById<TextView>(R.id.drawerName)
        val drawerPhoto = hearderView.findViewById<ShapeableImageView>(R.id.shapeableImageView)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)

        drawerName.text = SplashScreen.USERNAME

        if(SplashScreen.DPURL =="NaN"){
            drawerPhoto.setImageResource(R.drawable.ic_undraw_profile_pic_ic5t_2)
        }
        else{
            drawerPhoto.load(SplashScreen.BASEURL + SplashScreen.DPURL) {
                placeholder(R.drawable.ic_undraw_profile_pic_ic5t_2)
                error(R.drawable.ic_undraw_profile_pic_ic5t_2)
                crossfade(true)
            }
        }

        toggle.syncState()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = Intent(this,MainActivity::class.java)
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Sign Out")
            .setMessage("Are you sure you want to Sign Out ?")
            .setPositiveButton(R.string.sign_out) { dialog, id ->
                lifecycleScope.launch {
                    logInState(false)
                    startActivity(intent)
                    finish()
                }
            }
            .setNeutralButton(R.string.cancel) { dialog, id ->
            }
        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.about_us -> {
                    val intent = Intent(this, AboutUs::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                }
                R.id.privacy_policy -> {
                    val intent = Intent(this, PrivacyPolicy::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                }
                R.id.feedback -> {
                    val intent = Intent(this, Feedback::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                }
                R.id.change_password -> {
                    val intent = Intent(this, ChangePassword::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                }
                R.id.signout -> {
                    drawerLayout.closeDrawers()
                    val alertDialog: android.app.AlertDialog = builder.create()
                    alertDialog.show()
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

    override fun onBackPressed() {
        if(supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) is HomePage_fragment) {
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