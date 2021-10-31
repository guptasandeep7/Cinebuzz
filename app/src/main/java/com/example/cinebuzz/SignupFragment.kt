package com.example.cinebuzz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SignupFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.signup_fragment, container, false)

        val verifyEmail = view.findViewById<Button>(R.id.verifyEmail_btn)
        val backToLogin = view.findViewById<TextView>(R.id.back_to_login)

        verifyEmail.setOnClickListener(View.OnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container,VerifyFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

        })

        backToLogin.setOnClickListener(View.OnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container,LoginFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

        })
        return view
    }



}