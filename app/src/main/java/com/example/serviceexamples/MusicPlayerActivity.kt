package com.example.serviceexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serviceexamples.databinding.ActivityMusicPlayerBinding
import com.example.serviceexamples.service.PlayerService

class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startService(Intent(this, PlayerService::class.java))
        }

        binding.stopButton.setOnClickListener {
            stopService(Intent(this, PlayerService::class.java))
        }
    }
}