package com.moveitech.riderapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.moveitech.riderapp.utils.Constants.Companion.Location_NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class RiderApp : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelLocation = NotificationChannel(
                Location_NOTIFICATION_CHANNEL_ID,
                "Location Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            channelLocation.description = "This Notification get Location ."
            channelLocation.setShowBadge(true)
            val mNotificationManager = getSystemService(NotificationManager::class.java)
            mNotificationManager.createNotificationChannel(channelLocation)
        }
    }
}