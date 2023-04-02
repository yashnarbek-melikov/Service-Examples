package com.example.serviceexamples

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.serviceexamples.databinding.ActivityServiceNotificationBinding
import com.example.serviceexamples.service.SampleForegroundService
import com.example.serviceexamples.service.SampleForegroundService.Companion.ACTION_STOP_FOREGROUND

class ServiceNotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startService(Intent(this, SampleForegroundService::class.java))
            updateTextStatus()
        }

        binding.btnStop.setOnClickListener {
            val intentStop = Intent(this, SampleForegroundService::class.java)
            intentStop.action = ACTION_STOP_FOREGROUND
            startService(intentStop)
            Handler().postDelayed({
                updateTextStatus()
            },100)
        }
        updateTextStatus()
    }

    private fun updateTextStatus() {
        if(isMyServiceRunning(SampleForegroundService::class.java)){
            binding.txtServiceStatus.text = "Service is Running"
        }else{
            binding.txtServiceStatus.text = "Service is NOT Running"
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        try {
            val manager =
                getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(
                Int.MAX_VALUE
            )) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }
}