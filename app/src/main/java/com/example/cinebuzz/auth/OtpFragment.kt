package com.example.cinebuzz.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cinebuzz.R
import com.example.cinebuzz.auth.SignupFragment.Companion.userEmail
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.otp_fragment, container, false)

        val verify = view.findViewById<Button>(R.id.verify_btn)
        val otpEditText = view.findViewById<EditText>(R.id.otp_edittext)
        val otpProgressbar = view.findViewById<ProgressBar>(R.id.otp_progressBar)

        verify.setOnClickListener(View.OnClickListener {


            if(otpEditText.text.toString()==""){
                Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show()

            }
            else {
                verify.isEnabled=false
                otpProgressbar.visibility = View.VISIBLE

                val request = ServiceBuilder.buildService()
                val call = request.otp(
                    MyDataItem(
                                email = userEmail,
                                otp = otpEditText.text.toString().trim()
                    )
                )

                call.enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if(response.isSuccessful){

                            val fragmentManager = activity?.supportFragmentManager
                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            fragmentTransaction?.replace(R.id.fragment_container, PasswordFragment())
                            fragmentTransaction?.addToBackStack(null)
                            otpProgressbar.visibility=View.GONE
                            fragmentTransaction?.commit()


                        }
                        else{
                            Toast.makeText(context,"OTP is incorrect",Toast.LENGTH_SHORT).show()
                            otpProgressbar.visibility=View.GONE
                            verify.isEnabled=true

                        }

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {

                        Toast.makeText(context, "Connection Failed! Try again ",Toast.LENGTH_SHORT).show()
                        otpProgressbar.visibility=View.GONE
                        verify.isEnabled=true

                    }
                })



            }


        })
        return view
    }



}