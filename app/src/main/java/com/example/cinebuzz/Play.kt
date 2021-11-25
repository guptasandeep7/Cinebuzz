package com.example.cinebuzz

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class Play : AppCompatActivity(){

    lateinit var player: ExoPlayer
    lateinit var videoURL:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val frame:FrameLayout = findViewById(R.id.play_frame)
        val backBtn:ImageButton = findViewById(R.id.back_btn)
        videoURL = intent.getStringExtra("VIDEOURL").toString()
        videoURL.replace("%5C","/")
        videoURL = BASEURL+videoURL
        player = ExoPlayer.Builder(applicationContext).build()
        val exoPlayerView = findViewById<PlayerView>(R.id.video_view);
        exoPlayerView.player = player

        val mediaItem = MediaItem.fromUri(Uri.parse(videoURL))

        player.addMediaItem(mediaItem)
        player.prepare()
        player.play()

        backBtn.setOnClickListener{
            onBackPressed()
        }

        exoPlayerView.setOnClickListener{
            if(frame.isVisible){
                frame.visibility =View.GONE
            }
            else frame.visibility = View.VISIBLE
        }

    }

    override fun onPause() {
        super.onPause()
        player.release()
    }
}