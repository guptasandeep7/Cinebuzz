package com.example.cinebuzz.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.cinebuzz.R
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class SignupFragment : Fragment() {

    private fun isValidString(str: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    companion object {
        lateinit var userEmail: String
        lateinit var userName: String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.signup_fragment, container, false)

        val verifyEmail = view.findViewById<Button>(R.id.verifyEmail_btn)
        val backToLogin = view.findViewById<TextView>(R.id.back_to_login)
        val nameEditText = view.findViewById<EditText>(R.id.name_edittext)
        val emailEditText = view.findViewById<EditText>(R.id.email_edittext)
        val signupProgressbar = view.findViewById<ProgressBar>(R.id.signup_progressBar)


        verifyEmail.setOnClickListener {

            if (nameEditText.text.toString() == "" || emailEditText.text.toString() == "") {
                Toast.makeText(context, "Name/Email cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (!(isValidString(emailEditText.text.toString().trim()))) {
                emailEditText.hint = "Enter valid Email Id !!!"
            } else {
                verifyEmail.isClickable = false

                signupProgressbar.visibility = View.VISIBLE

                val request = ServiceBuilder.buildService()
                val call = request.signup(
                    MyDataItem(
                        name = nameEditText.text.toString().trim(),
                        email = emailEditText.text.toString().trim()
                    )
                )

                call.enqueue(object : retrofit2.Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if (response.isSuccessful) {

                            userName = nameEditText.text.toString()
                            userEmail = emailEditText.text.toString()
                            emailEditText.text.clear()
                            nameEditText.text.clear()

                            val fragmentManager = activity?.supportFragmentManager
                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            fragmentTransaction?.replace(R.id.fragment_container, OtpFragment())
                            signupProgressbar.visibility = View.GONE
                            fragmentTransaction?.commit()

                        } else {
                            emailEditText.text.clear()
                            nameEditText.text.clear()
                            emailEditText.hint = "Email Id already exist!!!"
                            verifyEmail.isClickable = true
                            signupProgressbar.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {

                        Toast.makeText(context, "Failed ${t.message}", Toast.LENGTH_SHORT).show()
                        verifyEmail.isClickable = true
                        signupProgressbar.visibility = View.GONE

                    }
                })


            }
        }

        backToLogin.setOnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, LoginFragment())
            fragmentTransaction?.commit()

        }
        return view
    }


}