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
            } else if (!(isValidString(emailEditText2.text.toString().trim()))) {
                emailEditText2.setHint("Enter valid Email Id !!!")
            } else {
                login.isClickable = false
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

                            loginProgressbar.visibility = View.GONE
                            startActivity(Intent(activity, DashboardActivity::class.java))
                            activity?.finish()

                        } else if (response.code() == 301) {
                            passwordEditText.setHint("Wrong Password !!!")
                            login.isClickable = true
                            loginProgressbar.visibility = View.GONE

                        } else if (response.code() == 401) {
                            emailEditText2.setHint("Email Id is not registered !!!")
                            login.isClickable = true
                            loginProgressbar.visibility = View.GONE
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
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