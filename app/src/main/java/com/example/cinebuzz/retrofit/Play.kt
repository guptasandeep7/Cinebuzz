package com.example.cinebuzz.retrofit

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinebuzz.R
import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import java.net.URLEncoder

class Play : AppCompatActivity(){

    lateinit var player: ExoPlayer
    lateinit var videoURL:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

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


    }

    override fun onPause() {
        super.onPause()
        player.release()
    }
}