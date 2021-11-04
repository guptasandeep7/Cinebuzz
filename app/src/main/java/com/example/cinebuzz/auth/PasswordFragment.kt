package com.example.cinebuzz.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R
import com.example.cinebuzz.auth.SignupFragment.Companion.userName
import com.example.cinebuzz.auth.VerifyFragment.Companion.forgot
import com.example.cinebuzz.databinding.PasswordFragmentBinding
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordFragment : Fragment(R.layout.password_fragment) {
    private var _binding: PasswordFragmentBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PasswordFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        passwordcheck()

        if (forgot == "true") {
            view.findViewById<TextView>(R.id.textView).setText("Reset Password")
        }
        val signBtn = view.findViewById<Button>(R.id.sign_btn)
        val confirmPassEditText = view.findViewById<TextInputEditText>(R.id.password2)
        val passwordProgressbar = view.findViewById<ProgressBar>(R.id.password_progressBar)

        signBtn.setOnClickListener {

            if (binding.password1.text.toString() == "" || confirmPassEditText.text.toString() == "") {
                Toast.makeText(context, "Please enter Password", Toast.LENGTH_SHORT)
                    .show()

            }
            else if(validEmail() != null)
            {
                Toast.makeText(context, "Enter correct password", Toast.LENGTH_SHORT)
                    .show()

            }
            else if (binding.password1.text.toString() == confirmPassEditText.text.toString()) {
                signBtn.isClickable = false
                passwordProgressbar.visibility = View.VISIBLE

                val request = ServiceBuilder.buildService()
                val call = when (forgot) {
                    "true" -> {

                        request.resetPassword(
                            MyDataItem(
                                email = SignupFragment.userEmail,
                                pass = binding.password1.text.toString(),
                                confirmpass = confirmPassEditText.text.toString(),
                            )
                        )

                    }
                    else -> {
                        request.password(
                            MyDataItem(
                                email = SignupFragment.userEmail,
                                pass = binding.password1.text.toString(),
                                confirmpass = confirmPassEditText.text.toString(),
                                name = userName
                            )
                        )
                    }
                }

                call.enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(
                        call: Call<ResponseBody?>,
                        response: Response<ResponseBody?>
                    ) {
                        if (response.isSuccessful) {

                            binding.password1.text!!.clear()
                            confirmPassEditText.text!!.clear()
                            passwordProgressbar.visibility = View.GONE

                            startActivity(Intent(activity, DashboardActivity::class.java))
                            activity?.finish()


                        } else {
                            Toast.makeText(context, response.code().toString(), Toast.LENGTH_SHORT)
                                .show()
                            signBtn.isClickable = true
                            passwordProgressbar.visibility = View.GONE

                        }

                    }

                    override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {

                        Toast.makeText(context, "Failed ${t.message}", Toast.LENGTH_SHORT).show()
                        signBtn.isClickable = true
                        passwordProgressbar.visibility = View.GONE

                    }
                })

            } else  Toast.makeText(context, "Confirm Password does not match", Toast.LENGTH_SHORT)
                .show()

        }
        return view
    }

    private fun validEmail(): String? {
        val password = binding.password1.text.toString()
        if (password.length < 8) {
            return "Password must Contain 8 Characters"

        }
        if (!password.matches(".*[A-Z].*".toRegex()) && (!password.matches(".*[\$#%@&*/+_=?^!].*".toRegex()))) {
            return "Must contain 1 Special character and 1 upper case character (\$#%@&*/+_=?^!)"
        } else if (!password.matches(".*[a-z].*".toRegex())) {
            return "Must contain 1 Lower case character"
        } else if (!password.matches(".*[\$#%@&*/+_=?^!].*".toRegex())) {
            return "Must contain 1 Special character (\$#%@&*/+_=?^!)"
        } else if (!password.matches(".*[A-Z].*".toRegex())) {
            return "Must contain 1 upper case character"
        }
        return null
    }

    private fun passwordcheck() {
        binding.password1.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordEdittext.helperText = validEmail()
            }

        }
    }
}