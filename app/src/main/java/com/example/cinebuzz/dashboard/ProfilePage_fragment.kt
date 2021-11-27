package com.example.cinebuzz.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.SplashScreen.Companion.DPURL
import com.example.cinebuzz.SplashScreen.Companion.USEREMAIL
import com.example.cinebuzz.SplashScreen.Companion.USERNAME
import com.example.cinebuzz.SplashScreen.Companion.saveUserDetails
import com.example.cinebuzz.dashboard.profile.PageAdapter
import com.example.cinebuzz.model.FileUtils
import com.example.cinebuzz.retrofit.ServiceBuilder
import com.example.cinebuzz.retrofit.WishlistDataItem
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfilePage_fragment : Fragment() {
    lateinit var movieCount: TextView
    lateinit var userPhoto: ShapeableImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = view.findViewById<ViewPager2>(R.id.viewpager_UserProfile)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_UserProfile)
        movieCount = view.findViewById(R.id.movieCount)
        val pageAdapter = PageAdapter(this)
        viewPager.adapter = pageAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Wishlist"
                1 -> tab.text = "History"
            }
        }.attach()
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_page, container, false)
        val changeDp = view.findViewById<ShapeableImageView>(R.id.change_dp)
        userPhoto = view.findViewById(R.id.user_image)
        val userName = view.findViewById<TextView>(R.id.user_name)
        val userEmail = view.findViewById<TextView>(R.id.user_email)


        userName.text = USERNAME
        userEmail.text = USEREMAIL
        if(DPURL=="NaN"){
            userPhoto.setImageResource(R.drawable.ic_undraw_profile_pic_ic5t_2)
        }
        else{
            userPhoto.load(BASEURL + DPURL) {
                placeholder(R.drawable.ic_undraw_profile_pic_ic5t_2)
                error(R.drawable.ic_undraw_profile_pic_ic5t_2)
                crossfade(true)
            }
        }

        count()
        changeDp.setOnClickListener {
            onClickRequestPermission(it)
        }
        return view
    }

    val dpUri = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            userPhoto.setImageURI(uri)
            sendDp(uri)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                dpUri.launch("image/*")
            }
        }

    fun onClickRequestPermission(view: View) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
               dpUri.launch("image/*")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                showSnackbar(
                    view,
                    getString(R.string.permission_required),
                    Snackbar.LENGTH_INDEFINITE,
                    "OK"
                ) {
                    requestPermissionLauncher.launch(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }


private fun showSnackbar(
    view: View,
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(view, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(view)
        }.show()
    } else {
        snackbar.show()
    }
}


private fun sendDp(uri: Uri) {
    val file = FileUtils.getFile(context, uri)
    val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    val imageFile = MultipartBody.Part.createFormData("dp", file.name, requestFile)
    val email = USEREMAIL.toRequestBody(okhttp3.MultipartBody.FORM)
    val request = ServiceBuilder.buildService()
    val call = request.changeDp(email, imageFile)
    call.enqueue(object : Callback<String?> {
        override fun onResponse(call: Call<String?>, response: Response<String?>) {
            if (response.isSuccessful) {
                Toast.makeText(context, imageFile.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(
                    context,
                    "successfull ${response.body().toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                DPURL = response.body().toString()
                lifecycleScope.launch {
                    saveUserDetails("DPURL", DPURL)
                }
                Toast.makeText(context, DPURL, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<String?>, t: Throwable) {
            Toast.makeText(context, "failed ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}

fun count() {
    val request2 = ServiceBuilder.buildService()
    val call2 = request2.movieCount(
        WishlistDataItem(
            userid = SplashScreen.USERID
        )
    )
    call2.enqueue(object : Callback<String?> {
        override fun onResponse(call: Call<String?>, response: Response<String?>) {
            if (response.isSuccessful) {
                val responseBody = response.body()!!
                movieCount.text = responseBody
            } else {
                Toast.makeText(
                    context,
                    "unsuccessful ${response.message()}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

        override fun onFailure(call: Call<String?>, t: Throwable) {
            Toast.makeText(context, "failed ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })

}
}

