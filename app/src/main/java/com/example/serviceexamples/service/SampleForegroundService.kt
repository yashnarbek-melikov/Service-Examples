package com.example.serviceexamples.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.serviceexamples.R
import com.example.serviceexamples.ServiceNotificationActivity

class SampleForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action != null && intent.action.equals(
                ACTION_STOP_FOREGROUND, ignoreCase = true
            )
        ) {
            stopSelf()
        }
        generateForegroundNotification()
        return START_STICKY
    }

    private lateinit var notification: Notification
    private val notificationId = 1

    private fun generateForegroundNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intentMainLanding = Intent(this, ServiceNotificationActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intentMainLanding, PendingIntent.FLAG_IMMUTABLE)
            val builder = notificationBuilder(pendingIntent)
            builder.color = resources.getColor(R.color.purple_200)
            notification = builder.build()
            startForeground(notificationId, notification)
        }
    }

    private fun notificationBuilder(pendingIntent: PendingIntent): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, "service_channel").setContentTitle(
            StringBuilder(resources.getString(R.string.app_name)).append(" service is running")
                .toString()
        ).setContentText("Touch to open") // swipe down for more options.
            .setSmallIcon(R.drawable.ic_alarm)
            .setWhen(0)
            .setContentIntent(pendingIntent)
    }

    companion object {
        const val ACTION_STOP_FOREGROUND = "ACTION_STOP"
    }
}
