package com.example.cinebuzz.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cinebuzz.R
import com.example.cinebuzz.retrofit.ApiInterface
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SignupFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.signup_fragment, container, false)

        val verifyEmail = view.findViewById<Button>(R.id.verifyEmail_btn)
        val backToLogin = view.findViewById<TextView>(R.id.back_to_login)
        val nameEditText = view.findViewById<EditText>(R.id.name_edittext)
        val emailEditText = view.findViewById<EditText>(R.id.email_edittext)


        verifyEmail.setOnClickListener(View.OnClickListener {

            if(nameEditText.text.toString().equals(null)||emailEditText.text.toString().equals(null)){
                Toast.makeText(context, "Name/Email cannot be empty",Toast.LENGTH_SHORT).show()
            }
            else {


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
                        if(response.isSuccessful){
                            if(response.code()==200){
                                val fragmentManager = activity?.supportFragmentManager
                                val fragmentTransaction = fragmentManager?.beginTransaction()
                                fragmentTransaction?.replace(R.id.fragment_container, OtpFragment())
                                fragmentTransaction?.addToBackStack(null)
                                fragmentTransaction?.commit()
                            }


                        }

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {

                        Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show()

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