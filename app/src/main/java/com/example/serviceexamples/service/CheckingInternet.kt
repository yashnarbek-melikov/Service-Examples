package com.example.serviceexamples.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.content.IntentFilter
import android.provider.SyncStateContract.Constants
import android.util.Log
import android.widget.Toast
import com.example.serviceexamples.broadcast.CheckInternetBroadcast
import com.example.serviceexamples.utils.NetworkHelper

class CheckingInternet: JobService(), CheckInternetBroadcast.ConnectivityReceiverListener {

    private lateinit var checkInternetBroadcast: CheckInternetBroadcast
    private lateinit var networkHelper: NetworkHelper

    override fun onCreate() {
        super.onCreate()
        Log.d("YYYY", "onCreate: service created")
        checkInternetBroadcast = CheckInternetBroadcast(this)
        networkHelper = NetworkHelper(applicationContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        registerReceiver(checkInternetBroadcast, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        unregisterReceiver(checkInternetBroadcast)
        return true
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if(isConnected) Toast.makeText(applicationContext, "connected", Toast.LENGTH_SHORT).show()
        else Toast.makeText(applicationContext, "disconnected", Toast.LENGTH_SHORT).show()
    }
}