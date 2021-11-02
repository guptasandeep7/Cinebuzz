package com.example.cinebuzz.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R
import com.example.cinebuzz.auth.SignupFragment.Companion.userName
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.sign

class PasswordFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.password_fragment, container, false)

        val signBtn = view.findViewById<Button>(R.id.sign_btn)
        val passwordEditText = view.findViewById<EditText>(R.id.password_edittext)
        val confirmPassEditText = view.findViewById<EditText>(R.id.confirmpass_edittext)
        val passwordProgressbar = view.findViewById<ProgressBar>(R.id.password_progressBar)




        signBtn.setOnClickListener(View.OnClickListener {

            if(passwordEditText.text.toString()==""||confirmPassEditText.text.toString()==""){
                Toast.makeText(context, "Please enter Password", Toast.LENGTH_SHORT).show()

            }
            else if(passwordEditText.text.toString() == confirmPassEditText.text.toString()){
                signBtn.isEnabled=false
                passwordProgressbar.visibility = View.VISIBLE

                val request = ServiceBuilder.buildService()
                val call = request.password(
                    MyDataItem(
                        email = SignupFragment.userEmail,
                        pass = passwordEditText.text.toString(),
                        confirmpass = confirmPassEditText.text.toString(),
                        name = userName
                    )
                )

                call.enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if(response.isSuccessful){

                            passwordEditText.text.clear()
                            confirmPassEditText.text.clear()
                            passwordProgressbar.visibility=View.GONE

                            startActivity(Intent(activity, DashboardActivity::class.java))

                        }
                        else{
                            Toast.makeText(context, response.code().toString(),Toast.LENGTH_SHORT).show()
                            signBtn.isEnabled=true
                            passwordProgressbar.visibility=View.GONE

                        }

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {

                        Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show()
                        signBtn.isEnabled=true

                    }
                })



            }
            else{
                Toast.makeText(context, "Confirm Password doesn't match", Toast.LENGTH_SHORT).show()

            }


        })
        return view
    }



}