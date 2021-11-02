package com.example.cinebuzz.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.verify_fragment, container, false)

        val next = view.findViewById<Button>(R.id.login_btn)
        val emailEdit=view.findViewById<EditText>(R.id.emailEdittext3)
        next.setOnClickListener(View.OnClickListener {
            if(emailEdit.text.toString()=="")
            {
                Toast.makeText(context, "Email/Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else {
                val request = ServiceBuilder.buildService()
                val call = request.verify(
                    MyDataItem(email = emailEdit.text.toString().trim())
                )
                call.enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if (response.isSuccessful) {

                                val fragmentManager = activity?.supportFragmentManager
                                val fragmentTransaction = fragmentManager?.beginTransaction()
                                fragmentTransaction?.replace(R.id.fragment_container, OtpFragment())
                                fragmentTransaction?.commit()

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        Toast.makeText(context, "Failed ${t.message}",Toast.LENGTH_SHORT).show()
                    }
                })
            }



        })
        return view
    }



}