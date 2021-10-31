package com.example.cinebuzz.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        val forgot = view.findViewById<TextView>(R.id.forgot_textview)
        val signup = view.findViewById<TextView>(R.id.signup_textview)
        val login = view.findViewById<Button>(R.id.login_button)



        forgot.setOnClickListener(View.OnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, VerifyFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

        })

        signup.setOnClickListener(View.OnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, SignupFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        })

        login.setOnClickListener(View.OnClickListener {
            startActivity(Intent(activity, DashboardActivity::class.java))

        })

        return view
    }



}