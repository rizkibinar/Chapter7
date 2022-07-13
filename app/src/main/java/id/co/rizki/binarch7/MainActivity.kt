package id.co.rizki.binarch7

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.co.rizki.binarch7.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    private val MAX_STREAMS = 1

    private lateinit var soundPool: SoundPool

    private var loaded = false

    private var soundId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // asumsi dapat response dari server dalam bentuk string dengan format json
        val jsonString = getJson()

        // parse data json, menggunakan JSONObject(string json)
        val superHeroJson = JSONObject(jsonString)

        println(superHeroJson)

        println("SquadName = ${superHeroJson.getString("squadName")}")

        val superHeroMembers = superHeroJson.getJSONArray("members")

        val superHeroMember = superHeroMembers.getJSONObject(1)

        println("Member 1 : ${superHeroMember.toString()}")

        println("Member 1 Name : ${superHeroMember.getString("name")}")

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME) // untuk merubah volume yang di gunakan pada soundpool, bisa alarm, ringtone atau media
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION) // merubah cara suara berinteraksi dan mengelompokkan
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .build()

        soundPool.setOnLoadCompleteListener { soundPool, i, i2 ->
            loaded = true // listen event saat suara sudah di load
        }

        soundId = soundPool.load(this, R.raw.gun, 1)


        binding.btnPlay.setOnClickListener {

            val serviceSystemManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            // ambil nilai suara saat ini, karna kita butuh suara dalam bentuk persen/float
            val actualVolume = serviceSystemManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()

            // ambil nilai max suara saat ini, karna kita butuh suara dalam bentuk persen/float
            val maxVolume = serviceSystemManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
            val volume = actualVolume / maxVolume

            // load suara pada saat sudah loaded
            if(loaded)
                soundPool.play(soundId, volume, volume, 1,0, 1f)
            else
                Toast.makeText(this, "Soundpool belum di load", Toast.LENGTH_SHORT).show()

        }

        binding.btnPlayRaw.setOnClickListener {
            val intent = Intent(this, VideoFromRawActivity::class.java)

            startActivity(intent)
        }

        binding.btnPlayInternet.setOnClickListener {

            val intent = Intent(this, VideoFromInternetActivity::class.java)

            startActivity(intent)
        }

    }

    private fun getJson() : String{
        val inputStream: InputStream = resources.openRawResource(R.raw.superhero)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } finally {
            inputStream.close()
        }

        return writer.toString()
    }
}