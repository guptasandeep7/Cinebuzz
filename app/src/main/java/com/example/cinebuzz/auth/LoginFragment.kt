package com.example.cinebuzz.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        val forgot = view.findViewById<TextView>(R.id.forgot_textview)
        val signup = view.findViewById<TextView>(R.id.signup_textview)
        val login = view.findViewById<Button>(R.id.login_button)
        val emailEditText2 = view.findViewById<EditText>(R.id.email_edittext2)
        val passwordEditText = view.findViewById<EditText>(R.id.password_edittext2)
        val loginProgressbar = view.findViewById<ProgressBar>(R.id.login_progressBar)


        forgot.setOnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, VerifyFragment())
            fragmentTransaction?.commit()

        }

        signup.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, SignupFragment())
            fragmentTransaction?.commit()
        }

        login.setOnClickListener {

            if (emailEditText2.text.toString() == "" || passwordEditText.text.toString() == "") {
                Toast.makeText(context, "Email/Password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                login.isEnabled = false
                loginProgressbar.visibility = View.VISIBLE
                val request = ServiceBuilder.buildService()
                val call = request.login(
                    MyDataItem(
                        email = emailEditText2.text.toString().trim(),
                        pass = passwordEditText.text.toString().trim()
                    )
                )
                call.enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if (response.isSuccessful) {
                            emailEditText2.text.clear()
                            passwordEditText.text.clear()
                            startActivity(Intent(activity, DashboardActivity::class.java))
                            loginProgressbar.visibility = View.GONE
                            login.isEnabled = true

                        } else if(response.code()==301){
                            Toast.makeText(context, "Wrong PAssword", Toast.LENGTH_SHORT)
                                .show()
                            login.isEnabled = true
                            loginProgressbar.visibility = View.GONE

                        }
                        else if(response.code()==401){
                            Toast.makeText(context, "User Does not exist", Toast.LENGTH_SHORT)
                                .show()
                            login.isEnabled = true
                            loginProgressbar.visibility = View.GONE
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        Toast.makeText(context, "Failed ${t.message}", Toast.LENGTH_SHORT).show()
                        login.isEnabled = true
                        loginProgressbar.visibility = View.GONE
                    }
                })

            }


        }

        return view
    }


}