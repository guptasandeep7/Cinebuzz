package com.example.cinebuzz.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.cinebuzz.R

class OtpFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.otp_fragment, container, false)

        val verify = view.findViewById<Button>(R.id.verify_btn)
        verify.setOnClickListener(View.OnClickListener {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, PasswordFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

        })
        return view
    }



}