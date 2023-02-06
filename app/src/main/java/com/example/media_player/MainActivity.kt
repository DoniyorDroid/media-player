package com.example.media_player

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.media_player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MediaPlayer.OnCompletionListener {
    lateinit var binding: ActivityMainBinding
    val musicURL =
        "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    lateinit var musicList: ArrayList<Int>

    var mediaPlayer: MediaPlayer? = null
    lateinit var handler: Handler
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(mainLooper)
        musicList = ArrayList()
        musicList.add(R.raw.music)
        musicList.add(R.raw.second_musict)

        binding.btnPlay.setOnClickListener {
            // internet orqali mp3 qoyish

/*            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(musicURL)
            mediaPlayer.setOnPreparedListener {
                it.start()
            }
            mediaPlayer.prepareAsync()
            */
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, musicList[position])
                mediaPlayer?.start()

                mediaPlayer?.setOnCompletionListener(this)

                binding.btnSeekbar.max = mediaPlayer?.duration!!

                binding.btnSeekbar.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        if (p2) {
                            mediaPlayer?.seekTo(p1)
                        }
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {

                    }

                })
                handler.postDelayed(runnable, 1000)
            }
        }

        binding.btnPause.setOnClickListener {
            if (mediaPlayer?.isPlaying!!) {
                mediaPlayer?.pause()
            }
        }
        binding.btnResume.setOnClickListener {
            if (mediaPlayer != null) {
                if (!mediaPlayer!!.isPlaying) {
                    mediaPlayer?.start()
                }
            }
        }
        binding.btnStop.setOnClickListener {
            if (mediaPlayer?.isPlaying!!) {
                mediaPlayer?.stop()

                mediaPlayer?.release()
                mediaPlayer = null
            }
        }
        binding.btnBackward.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition!! - 3000)
        }
        binding.btnForward.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition!! + 3000)
            Log.d("AAA", "onCreate: ${mediaPlayer?.currentPosition!!}")
        }

    }

    private val runnable = object : Runnable {
        override fun run() {
            val currentPosition = mediaPlayer?.currentPosition!! / 1000
            binding.btnSeekbar.progress = mediaPlayer?.currentPosition!!

            val sekund = currentPosition % 60
            val minut = currentPosition / 60

            binding.tvTime.text = "$minut:$sekund"
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCompletion(p0: MediaPlayer?) {
        mediaPlayer = MediaPlayer.create(this, musicList[++position])
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener(this)


        binding.btnSeekbar.max = mediaPlayer?.duration!!

        binding.btnSeekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    mediaPlayer?.seekTo(p1)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        handler.postDelayed(runnable, 1000)
    }
}