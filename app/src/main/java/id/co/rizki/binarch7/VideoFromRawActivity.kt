package id.co.rizki.binarch7

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.rizki.binarch7.databinding.ActivityVideoFromRawBinding

class VideoFromRawActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVideoFromRawBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoFromRawBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // set video path dari video yang ingin di jalankan
        val path = "android.resource://$packageName/${R.raw.example_video_footage}"



        //set path tadi ke URI
        binding.videoView.setVideoURI(Uri.parse(path))


        binding.btnPlay.setOnClickListener {
            binding.videoView.start()
        }

        binding.btnPause.setOnClickListener {
            binding.videoView.pause()
        }
        binding.btnStop.setOnClickListener {
            binding.videoView.stopPlayback()

            // pastikan reset video/reinit setelah stop playback
            binding.videoView.setVideoURI(Uri.parse(path))
        }

    }
}