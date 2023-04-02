package com.example.serviceexamples

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceexamples.databinding.ActivityCheckInternetBinding
import com.example.serviceexamples.service.CheckingInternet


class CheckInternetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckInternetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scheduleJob()
    }

    private fun scheduleJob() {
        val myJob = JobInfo.Builder(0, ComponentName(this, CheckingInternet::class.java))
            .setRequiresCharging(false)
            .setMinimumLatency(1000)
            .setOverrideDeadline(2000)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            .build()

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(myJob)
    }

    override fun onStart() {
        super.onStart()
        startService(Intent(this, CheckingInternet::class.java))
    }

    override fun onStop() {
        stopService(Intent(this, CheckingInternet::class.java))
        super.onStop()
    }
}