package com.example.cinebuzz.auth

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
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
import retrofit2.Callback
import retrofit2.Response

class SignupFragment: Fragment() {
    companion object {
        lateinit var userEmail: String
        lateinit var userName:String
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.signup_fragment, container, false)

        val verifyEmail = view.findViewById<Button>(R.id.verifyEmail_btn)
        val backToLogin = view.findViewById<TextView>(R.id.back_to_login)
        val nameEditText = view.findViewById<EditText>(R.id.name_edittext)
        val emailEditText = view.findViewById<EditText>(R.id.email_edittext)
        val signupProgressbar = view.findViewById<ProgressBar>(R.id.signup_progressBar)


        verifyEmail.setOnClickListener(View.OnClickListener {

            if(nameEditText.text.toString() == "" || emailEditText.text.toString() == ""){
                Toast.makeText(context, "Name/Email cannot be empty",Toast.LENGTH_SHORT).show()
            }
            else {
                verifyEmail.isEnabled=false

                signupProgressbar.visibility=View.VISIBLE

                val request = ServiceBuilder.buildService()
                val call = request.signup(
                    MyDataItem(
                        name = nameEditText.text.toString().trim(),
                        email = emailEditText.text.toString().trim()
                    )
                )

                call.enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if(response.isSuccessful){

                            userName=nameEditText.text.toString()
                            userEmail=emailEditText.text.toString()
                                emailEditText.text.clear()
                                nameEditText.text.clear()

                            val fragmentManager = activity?.supportFragmentManager
                                val fragmentTransaction = fragmentManager?.beginTransaction()
                                fragmentTransaction?.replace(R.id.fragment_container, OtpFragment())
                                fragmentTransaction?.addToBackStack(null)
                                signupProgressbar.visibility=View.GONE
                                fragmentTransaction?.commit()



                        }
                        else{
                            Toast.makeText(context, response.code().toString(),Toast.LENGTH_SHORT).show()
                            verifyEmail.isEnabled=true
                            signupProgressbar.visibility=View.GONE

                        }

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {

                        Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show()
                        verifyEmail.isEnabled=true

                    }
                })



            }
        })

        backToLogin.setOnClickListener(View.OnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, LoginFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

        })
        return view
    }



}