package com.example.cinebuzz.auth

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cinebuzz.R
import com.example.cinebuzz.auth.SignupFragment.Companion.userEmail
import com.example.cinebuzz.auth.SignupFragment.Companion.userName
import com.example.cinebuzz.model.SomthingWentWrong
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder2
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpFragment : Fragment() {
    private lateinit var timerCountDown: CountDownTimer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.otp_fragment, container, false)

        val verify = view.findViewById<Button>(R.id.verify_btn)
        val otpEditText = view.findViewById<TextInputEditText>(R.id.otp)
        val otpProgressbar = view.findViewById<ProgressBar>(R.id.otp_progressBar)
        val timer = view.findViewById<TextView>(R.id.timer)

        timerCountDown = object : CountDownTimer(31000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timer.isEnabled = false
                timer.text = "Resend OTP in " + millisUntilFinished / 1000 + " sec"
            }

            override fun onFinish() {
                timer.text = getString(R.string.resend_otp)
                timer.isEnabled = true
            }
        }.start()

        timer.setOnClickListener {
            otpProgressbar.visibility = View.VISIBLE

            val request = ServiceBuilder2.buildService()
            val call = request.signup(
                MyDataItem(
                    name = userName,
                    email = userEmail
                )
            )

            call.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    otpProgressbar.visibility = View.GONE

                    if (response.isSuccessful) {
                        Toast.makeText(context, "OTP resend successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Failed to sent OTP", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    otpProgressbar.visibility = View.GONE
                    val fragmentManager = activity?.supportFragmentManager
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragment_container, SomthingWentWrong())
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                }
            })
        }


        verify.setOnClickListener {


            if (otpEditText.text.toString() == "") {
                otpEditText.error = "Please enter OTP"

            } else {
                verify.isClickable = false
                otpProgressbar.visibility = View.VISIBLE

                val request = ServiceBuilder2.buildService()
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
                        if (response.isSuccessful) {

                            timerCountDown.cancel()
                            val fragmentManager = activity?.supportFragmentManager
                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            fragmentTransaction?.replace(
                                R.id.fragment_container,
                                PasswordFragment()
                            )
                            otpProgressbar.visibility = View.GONE
                            fragmentTransaction?.commit()


                        } else {
                            otpEditText.error = "OTP is incorrect"
                            otpProgressbar.visibility = View.GONE
                            verify.isClickable = true

                        }

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {

                        val fragmentManager = activity?.supportFragmentManager
                        val fragmentTransaction = fragmentManager?.beginTransaction()
                        fragmentTransaction?.replace(R.id.fragment_container, SomthingWentWrong())
                        fragmentTransaction?.addToBackStack(null)
                        fragmentTransaction?.commit()
                        otpProgressbar.visibility = View.GONE
                        verify.isClickable = true

                    }
                })


            }


        }
        return view
    }


}