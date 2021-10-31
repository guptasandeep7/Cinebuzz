package com.example.cinebuzz.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R

class PasswordFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.password_fragment, container, false)

        val signBtn = view.findViewById<Button>(R.id.sign_btn)
        signBtn.setOnClickListener(View.OnClickListener {

            startActivity(Intent(activity, DashboardActivity::class.java))

        })
        return view
    }



}