package com.example.cinebuzz.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.DPURL
import com.example.cinebuzz.SplashScreen.Companion.TOKEN
import com.example.cinebuzz.SplashScreen.Companion.USEREMAIL
import com.example.cinebuzz.SplashScreen.Companion.USERID
import com.example.cinebuzz.SplashScreen.Companion.USERNAME
import com.example.cinebuzz.SplashScreen.Companion.logInState
import com.example.cinebuzz.SplashScreen.Companion.saveUserDetails
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.ServiceBuilder2
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private fun isValidString(str: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        val forgot = view.findViewById<TextView>(R.id.forgot_textview)
        val signup = view.findViewById<TextView>(R.id.signup_textview)
        val login = view.findViewById<Button>(R.id.login_button)
        val emailEditText2 = view.findViewById<TextInputEditText>(R.id.editText)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.password)
        val loginProgressbar = view.findViewById<ProgressBar>(R.id.login_progressBar)


        forgot.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            fragmentManager?.popBackStack()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, VerifyFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        signup.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            fragmentManager?.popBackStack()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, SignupFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        login.setOnClickListener {

            if (emailEditText2.text.toString() == "")
                emailEditText2.error = "Enter email movieId"
            else if (passwordEditText.text.toString() == "")
                Toast.makeText(context, "Enter Password!!!", Toast.LENGTH_SHORT).show()
            else if (!(isValidString(emailEditText2.text.toString().trim()))) {
                emailEditText2.error = "Enter valid Email Id !!!"
            } else {
                login.isClickable = false
                loginProgressbar.visibility = View.VISIBLE
                val request = ServiceBuilder2.buildService()
                val call = request.login(
                    MyDataItem(
                        email = emailEditText2.text.toString().trim().lowercase(),
                        pass = passwordEditText.text.toString().trim()
                    )
                )
                call.enqueue(object : Callback<MyDataItem?> {
                    override fun onResponse(
                        call: Call<MyDataItem?>,
                        response: Response<MyDataItem?>
                    ) {
                        if (response.isSuccessful) {
                            val userData = response.body()
                            lifecycleScope.launch {
                                logInState(true)
                                TOKEN = userData?.token.toString()
                                USERNAME = userData?.name.toString()
                                USEREMAIL = userData?.email.toString()
                                USERID = userData?.id.toString()
                                DPURL = userData?.dpUrl?:"NaN"
                                saveUserDetails("USERNAME", USERNAME)
                                saveUserDetails("USEREMAIL", USEREMAIL)
                                saveUserDetails("TOKEN", TOKEN)
                                saveUserDetails("USERID", USERID)
                                saveUserDetails("DPURL", DPURL)
                                startActivity(Intent(activity, DashboardActivity::class.java))
                                activity?.finish()
                            }
                            loginProgressbar.visibility = View.GONE
                        } else if (response.code() == 301) {
                            Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show()
                            login.isClickable = true
                            loginProgressbar.visibility = View.GONE

                        } else if (response.code() == 401) {
                            emailEditText2.error = "Email Id is not registered !!!"
                            login.isClickable = true
                            loginProgressbar.visibility = View.GONE
                        }

                    }

                    override fun onFailure(call: Call<MyDataItem?>, t: Throwable) {
                        Toast.makeText(context, "Failed ${t.message}", Toast.LENGTH_SHORT).show()
                        login.isClickable = true
                        loginProgressbar.visibility = View.GONE
                    }
                })

            }


        }

        return view
    }


}