package com.example.cinebuzz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.databinding.SomethingWentWrongBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import java.lang.Exception

class Play : AppCompatActivity() {

    lateinit var player: ExoPlayer
    lateinit var videoURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val frame: FrameLayout = findViewById(R.id.play_frame)
        val backBtn: ImageButton = findViewById(R.id.back_btn)
        videoURL = intent.getStringExtra("VIDEOURL").toString()
        videoURL.replace("%5C", "/")
        videoURL = BASEURL + videoURL
        player = ExoPlayer.Builder(applicationContext).build()
        val exoPlayerView = findViewById<PlayerView>(R.id.video_view);
        exoPlayerView.player = player

        val mediaItem = MediaItem.fromUri(Uri.parse(videoURL))

        with(player) {
            addMediaItem(mediaItem)
            try {
                prepare()
                play()
            }catch (e:Exception){
                startActivity(Intent(this@Play,SomethingWentWrongBinding::class.java))
            }

            addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) =
                    if (isPlaying) window.addFlags(FLAG_KEEP_SCREEN_ON)
                    else window.clearFlags(FLAG_KEEP_SCREEN_ON)
            })
        }

        backBtn.setOnClickListener {
            onBackPressed()
        }

        exoPlayerView.setOnClickListener {
            if (frame.isVisible) {
                frame.visibility = View.GONE
            } else frame.visibility = View.VISIBLE
        }

    }


    override fun onPause() {
        super.onPause()
        player.release()
    }

}