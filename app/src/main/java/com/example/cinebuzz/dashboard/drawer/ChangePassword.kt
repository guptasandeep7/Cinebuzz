package com.example.cinebuzz.dashboard.drawer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cinebuzz.DashboardActivity
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen
import com.example.cinebuzz.auth.SignupFragment
import com.example.cinebuzz.auth.VerifyFragment
import com.example.cinebuzz.model.SomthingWentWrong
import com.example.cinebuzz.retrofit.MyDataItem
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassword : AppCompatActivity() {
  private lateinit var oldPassword:TextInputEditText
    private lateinit var newPassword:TextInputEditText
    private lateinit var newPasswordLayout:TextInputLayout
    private lateinit var done:Button
    private lateinit var passwordProgressbar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)
         done = findViewById(R.id.done)
        oldPassword = findViewById(R.id.password4)
        newPassword = findViewById(R.id.password3)
        newPasswordLayout = findViewById(R.id.password_edittext3)
        passwordProgressbar = findViewById(R.id.password_progressBar2)
        passcheck()
        done.setOnClickListener {

            if (newPassword.text.toString() == "" || oldPassword.text.toString() == "") {
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT)
                    .show()

            } else if (validPass() != null) {
                Toast.makeText(this, validPass().toString(), Toast.LENGTH_SHORT)
                    .show()
            } else {
                passwordProgressbar.visibility = View.VISIBLE
                val request = ServiceBuilder.buildService()
                val call = request.changePassword(
                    MyDataItem(
                        email = SplashScreen.USEREMAIL,
                        oldPass = oldPassword.text.toString(),
                        newPass = newPassword.text.toString(),
                    )
                )
                call.enqueue(object : Callback<String?> {
                    override fun onResponse(
                        call: Call<String?>,
                        response: Response<String?>
                    ) {
                        if (response.isSuccessful) {

                            newPassword.text!!.clear()
                            oldPassword.text!!.clear()
                            passwordProgressbar.visibility = View.GONE
                            startActivity(
                                Intent(
                                    this@ChangePassword,
                                    DashboardActivity::class.java
                                )
                            )
                            this@ChangePassword.finish()

                        } else if (response.code() == 301) {
                            Toast.makeText(
                                this@ChangePassword,
                                "Old Password is incorrect",
                                Toast.LENGTH_SHORT
                            ).show()
                            done.isClickable = true
                            passwordProgressbar.visibility = View.GONE
                        } else {
                            Toast.makeText(
                                this@ChangePassword,
                                response.code().toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            done.isClickable = true
                            passwordProgressbar.visibility = View.GONE

                        }

                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {

                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.change, SomthingWentWrong())
                        transaction.commit()
                        done.isClickable = true
                        passwordProgressbar.visibility = View.GONE

                    }
                })
            }
        }
                }
    private fun validPass(): String? {
        val password = newPassword.text.toString()
        if (password.length < 6 || password.length >12) {
            return "Password must be in range of 6 to 12 Characters"

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

    private fun passcheck() {
        newPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                newPasswordLayout.helperText = validPass()
            }

        }
    }
}
