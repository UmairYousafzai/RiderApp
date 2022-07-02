package com.moveitech.riderapp.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.moveitech.riderapp.R
import com.moveitech.riderapp.dataModel.location.LocationData
import com.moveitech.riderapp.network.ResultWrapper
import com.moveitech.riderapp.repository.ApiDataRepository
import com.moveitech.riderapp.utils.*
import com.moveitech.riderapp.utils.Constants.Companion.ACTION_START_LOCATION_SERVICE
import com.moveitech.riderapp.utils.Constants.Companion.ACTION_STOP_LOCATION_SERVICE
import com.moveitech.riderapp.utils.Constants.Companion.EXTRA_LOCATION_TYPE
import com.moveitech.riderapp.utils.Constants.Companion.Location_NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var locationType:Int=0
    private var trackingCode:String="0"
    private var interval: Long = 60000

    @Inject
    lateinit var repository: ApiDataRepository

    @Inject
    lateinit var dataStore: DataStoreHelper

    private val locationCallback: LocationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            val location: Location? = locationResult.lastLocation
            val extras: Bundle = location!!.extras
            val isMockLocation = extras.getBoolean(
                FusedLocationProviderClient.KEY_MOCK_LOCATION,
                false
            )
            if (!isMockLocation) {

                saveLocation(location)


            } else {

                Toast.makeText(
                    baseContext,
                    "Mock location is ON \n Please Disable Mock Location",
                    Toast.LENGTH_SHORT
                ).show()
                stopLocationUpdates()
                stopForeground(true)
                stopSelf()
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        Log.e("service : ", "on create")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {

            val action = intent.action
            if (action != null) {
                if (action == ACTION_START_LOCATION_SERVICE) {
                    locationType= intent.getIntExtra(EXTRA_LOCATION_TYPE,0)
                    trackingCode= intent.getStringExtra(Constants.EXTRA_TRACKING_ID)!!
                    showNotification()
                    scope.launch {
                        dataStore.saveIsServiceRunning(true)

                    }

                }
                if (action == ACTION_STOP_LOCATION_SERVICE) {
                    scope.launch {
                        dataStore.saveIsServiceRunning(false)

                    }

                    stopLocationUpdates()
                    stopForeground(true)
                    stopSelf()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("service : ", "on destroy")
    }

    override fun onBind(intent: Intent?): IBinder {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    @SuppressLint("MissingPermission")
    private fun showNotification() {

        val intent: Intent = Intent(this, LocationService::class.java)
        intent.action = ACTION_STOP_LOCATION_SERVICE

        val turnOfServicesPendingIntent: PendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.getService(this, 100, intent, PendingIntent.FLAG_IMMUTABLE)
            } else {
                PendingIntent.getService(this, 100, intent, 0)
            }

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(Location_NOTIFICATION_CHANNEL_ID)
        }


        builder.apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle("Rider App")
            setContentText("Rider storing your location")
            setCategory(Notification.CATEGORY_SERVICE)
            addAction(NotificationCompat.Action(0, "Close", turnOfServicesPendingIntent))
        }


// notificationId is a unique int for each notification that you must define
        val notification: Notification = builder.build()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (baseContext.isLocationEnabled()) {
                initiateLocationUpdates()
            }
        } else {
            Toast.makeText(
                this,
                "Please Allow Permission Access in App Setting.",
                Toast.LENGTH_SHORT
            ).show()
        }
        startForeground(1, builder.build())
    }

    @SuppressLint("MissingPermission")
    private fun initiateLocationUpdates() {

        val locationRequest: LocationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = this@LocationService.interval
            fastestInterval = this@LocationService.interval
        }

        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
    }
    fun stopLocationUpdates() {
        LocationServices.getFusedLocationProviderClient(this)
            .removeLocationUpdates(locationCallback)
    }

    private fun saveLocation(location: Location) {
        val coordinates: String = location.latitude.toString() + "," + location.longitude

      val address=  this.getCompleteAddressString(location.latitude,location.longitude)
        val date= getDate()

        val locationRequest: com.moveitech.riderapp.dataModel.location.LocationRequest= com.moveitech.riderapp.dataModel.location.LocationRequest()


        locationRequest.add(LocationData(
            "0000000001",
            date,
            location.latitude.toString(),
            location.longitude.toString(),
            1,
            locationType,
            trackingCode
        ))

        scope.launch {
            repository.saveLocation(locationRequest).let {response->
                when (response) {
                    is ResultWrapper.Success -> {

                    }
                    else -> {

                    }
                }
            }
        }

    }
}