package com.example.cinebuzz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.cinebuzz.dashboard.PlayMovie
import com.example.cinebuzz.databinding.ActivityMainBinding
import com.example.cinebuzz.model.OfflinePage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    companion object {
        val BASEURL = "https://cine---buzz.herokuapp.com/"
        lateinit var USERNAME: String
        lateinit var USEREMAIL: String
        lateinit var USERID:String
        lateinit var DPURL: String
        lateinit var TOKEN: String
        private var binding: ActivityMainBinding? = null
        lateinit var dataStore: DataStore<Preferences>
        suspend fun logInState(value: Boolean) {
            val dataStoreKey = preferencesKey<Boolean>("LOGIN")
            dataStore.edit { UserDetails ->
                UserDetails[dataStoreKey] = value
            }

        }

        suspend fun saveUserDetails(key: String, value: String) {
            val dataStoreKey = preferencesKey<String>(key)
            dataStore.edit { UserDetails ->
                UserDetails[dataStoreKey] = value
            }

        }

        suspend fun isLogedIn(): Boolean? {
            val dataStoreKey = preferencesKey<Boolean>("LOGIN")
            val preferences = dataStore.data.first()
            return preferences[dataStoreKey]
        }

        suspend fun getUserDetails(key: String): String? {
            val dataStoreKey = preferencesKey<String>(key)
            val preferences = dataStore.data.first()
            return preferences[dataStoreKey]
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view= binding!!.root
        setContentView(view)
        dataStore = applicationContext?.createDataStore(name = "UserDetails")!!
        lifecycleScope.launch {
            if (isLogedIn() == true) {
                USERNAME = getUserDetails("USERNAME")!!
                USEREMAIL = getUserDetails("USEREMAIL")!!
                TOKEN = getUserDetails("TOKEN")!!
                USERID = getUserDetails("USERID")!!
                val intent=Intent(this@SplashScreen, OfflinePage::class.java)
                startActivity(intent)
                finish()
            }
            else {
                TOKEN ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImdvbGNoaWdlZWtAZ21haWwuY29tIiwiaWF0IjoxNjM3MzUxMDQ4LCJleHAiOjE2Mzc0Mzc0NDh9.v1fuXxizIYD4cwzca_hZCS9CSVObUMbzqror4hQ6YUY"

                val intent=Intent(this@SplashScreen,OfflinePage::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}