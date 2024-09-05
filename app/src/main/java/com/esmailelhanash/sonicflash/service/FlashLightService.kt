package com.esmailelhanash.sonicflash.service

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
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.esmailelhanash.sonicflash.R
import com.esmailelhanash.sonicflash.data.camera.CameraManager
import com.esmailelhanash.sonicflash.data.flashlight.FlashLightController
import com.esmailelhanash.sonicflash.data.model.FlashEffect
import com.esmailelhanash.sonicflash.domain.camera.ICameraProvider
import com.esmailelhanash.sonicflash.presentation.viewmodel.FlashlightViewModel
import com.esmailelhanash.sonicflash.presentation.viewmodel.FlashlightViewModelFactory

class FlashlightService : LifecycleService(), ViewModelStoreOwner {
    private val notificationChannelId = "my_notification_channel"

    private lateinit var cameraProvider : ICameraProvider
    private val binder = LocalBinder()

    // FlashlightViewModel instance
    private lateinit var flashlightViewModel : FlashlightViewModel
        // getter with exception check
//   fun getFlashlightViewModel() : FlashlightViewModel? {
//        return if (::flashlightViewModel.isInitialized) {
//            flashlightViewModel
//        } else {
//            null
//        }
//    }
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

    fun setFlashEffect(flashEffect: FlashEffect) {
        flashlightViewModel.setFlashEffect(flashEffect)
    }

    fun toggleFlash() {
        flashlightViewModel.toggleFlash()
    }

    fun isFlashOn(): LiveData<Boolean> {
        return flashlightViewModel.isFlashLightOn
    }

    fun getFlashEffect() : LiveData<FlashEffect> {
        return flashlightViewModel.flashEffect
    }

}
