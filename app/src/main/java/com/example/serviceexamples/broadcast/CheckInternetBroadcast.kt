package com.example.serviceexamples.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


class CheckInternetBroadcast(private val connectivityReceiverListener: ConnectivityReceiverListener): BroadcastReceiver() {

//    private lateinit var networkHelper: NetworkHelper

    override fun onReceive(context: Context, intent: Intent) {

//        networkHelper = NetworkHelper(context)
        connectivityReceiverListener.onNetworkConnectionChanged(isConnected(context))
//        networkHelper = NetworkHelper(context)
//
//        if(networkHelper.isNetworkConnected()) {
//            Toast.makeText(context, "network connected", Toast.LENGTH_SHORT).show()
//            Log.d("YYYY", "onReceive: network connected")
//        } else {
//            Toast.makeText(context, "network disconnected", Toast.LENGTH_SHORT).show()
//            Log.d("YYYY", "onReceive: network disconnected")
//        }
    }

    private fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}