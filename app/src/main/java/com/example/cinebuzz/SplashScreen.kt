package com.example.cinebuzz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.cinebuzz.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    companion object {
        lateinit var USERNAME: String
        lateinit var USEREMAIL: String
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
        dataStore = applicationContext?.createDataStore(name = "UserDetails")!!
        lifecycleScope.launch {
            if (isLogedIn() == true) {
                USERNAME = getUserDetails("USERNAME")!!
                USEREMAIL = getUserDetails("USEREMAIL")!!
                TOKEN = getUserDetails("TOKEN")!!
                startActivity(Intent(applicationContext, DashboardActivity::class.java))
                finish();

            } else {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish();
            }
        }
    }
}