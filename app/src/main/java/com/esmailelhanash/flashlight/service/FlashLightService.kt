package com.esmailelhanash.flashlight.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.esmailelhanash.flashlight.R
import com.esmailelhanash.flashlight.data.camera.CameraManager
import com.esmailelhanash.flashlight.data.flashlight.FlashLightController
import com.esmailelhanash.flashlight.data.model.FlashEffect
import com.esmailelhanash.flashlight.domain.camera.ICameraProvider
import com.esmailelhanash.flashlight.presentation.viewmodel.FlashlightViewModel
import com.esmailelhanash.flashlight.presentation.viewmodel.FlashlightViewModelFactory

class FlashlightService : LifecycleService(), ViewModelStoreOwner {
    private val notificationChannelId = "my_notification_channel"

    private lateinit var cameraProvider : ICameraProvider
    private val binder = LocalBinder()

    // FlashlightViewModel instance
    private lateinit var flashlightViewModel : FlashlightViewModel

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): FlashlightService = this@FlashlightService
    }
    override fun onCreate() {
        super.onCreate()



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        startForeground(1, createNotification())
        initializeCameraManager()
        initializeFlashManager()

        flashlightViewModel = FlashlightViewModelFactory(
            FlashLightController(cameraProvider)
        ).create(FlashlightViewModel::class.java)

    }

    private fun initializeFlashManager() {

    }

    private fun initializeCameraManager() {
        cameraProvider = CameraManager(this)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(notificationChannelId, "My Channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val builder = NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My Service")
            .setContentText("Service is running")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        // Add actions or other customizations as needed

        return builder.build()
    }

    override val viewModelStore: ViewModelStore
        get() = ViewModelStore()

    fun executeFlashEffect(flashEffect: FlashEffect) {
        flashlightViewModel.setFlashEffect(flashEffect)
    }

    fun triggerFlash(b:Boolean) {
        flashlightViewModel.setFlashLight(b)
    }

}
