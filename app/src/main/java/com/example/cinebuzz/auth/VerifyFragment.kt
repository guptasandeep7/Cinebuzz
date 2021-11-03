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
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyFragment : Fragment() {

    companion object {
        lateinit var forgot: String
    }
    private fun isValidString(str: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.verify_fragment, container, false)

        forgot="false"
        val next = view.findViewById<Button>(R.id.login_btn)
        val emailEdit = view.findViewById<EditText>(R.id.emailEdittext3)
        val verifyProgressBar = view.findViewById<ProgressBar>(R.id.verify_progressBar)
        next.setOnClickListener{
            if (emailEdit.text.toString() == "") {
                emailEdit.error = "Enter Email Id"
            } else if (!(isValidString(emailEdit.text.toString().trim()))) {
                emailEdit.error = "Enter valid Email Id !!!"
            } else {
                next.isClickable = false
                verifyProgressBar.visibility = View.VISIBLE
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

                            SignupFragment.userEmail = emailEdit.text.toString()
                            emailEdit.text.clear()
                            verifyProgressBar.visibility = View.GONE

                            val fragmentManager = activity?.supportFragmentManager
                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            forgot = "true"
                            fragmentTransaction?.replace(R.id.fragment_container, OtpFragment())
                            fragmentTransaction?.commit()

                        } else {
                            emailEdit.error = "Email Id is not registered !!!"
                            next.isClickable = true
                            verifyProgressBar.visibility = View.GONE
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                        Toast.makeText(context, "Failed ${t.message}", Toast.LENGTH_SHORT).show()
                        next.isClickable = true
                        verifyProgressBar.visibility = View.GONE
                    }
                })
            }


        }
        return view
    }


}