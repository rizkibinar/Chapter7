package id.co.rizki.binarch7

import android.net.Uri
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import id.co.rizki.binarch7.databinding.ActivityVideoFromInternetBinding
import id.co.rizki.binarch7.databinding.ActivityVideoFromRawBinding

class VideoFromInternetActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVideoFromInternetBinding

    private val URL = "https://techslides.com/demos/sample-videos/small.mp4"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoFromInternetBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Set up the media controller widget and attach it to the video view.
        val controller = MediaController(this)
        controller.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(controller)
    }

    override fun onStart() {
        super.onStart()

        initializePlayer()
    }

    private fun initializePlayer() {

        binding.loading.visibility = View.VISIBLE

        val videoUri = Uri.parse(URL)

        binding.videoView.setVideoURI(videoUri)

        binding.videoView.setOnPreparedListener {

            binding.loading.visibility = View.GONE

            binding.videoView.seekTo(1)

            binding.videoView.start()


        }


    }
}