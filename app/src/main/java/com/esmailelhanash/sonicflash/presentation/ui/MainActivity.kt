package com.esmailelhanash.sonicflash.presentation.ui

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.esmailelhanash.sonicflash.presentation.PermissionsHandler
import com.esmailelhanash.sonicflash.service.FlashlightService
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val flashlightServiceState = mutableStateOf<FlashlightService?>(null)

    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val localBinder = binder as FlashlightService.LocalBinder
            flashlightServiceState.value = localBinder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            flashlightServiceState.value = null
            isBound = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeAdMob()
        setContent {
            var cameraPermissionsGranted by remember {
                mutableStateOf(PermissionsHandler.checkPermissions(this))
            }

            if (cameraPermissionsGranted)
                startFlashlightService(this@MainActivity)
            else PermissionsHandler.RequestMissingPermissions(this@MainActivity){
                    startFlashlightService(this@MainActivity)
                }




            MainContent(cameraPermissionsGranted,flashlightServiceState.value) checkPermissions@{
                cameraPermissionsGranted = PermissionsHandler.checkPermissions(this)
            }
        }
    }


    private fun initializeAdMob() {
        // Initialize AdMob
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {
                Log.d("initializeAdMob", "initializeAdMob: $it")
            }
        }
    }

    private fun startFlashlightService(c: Context) {
        val intent = Intent(c, FlashlightService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            c.startForegroundService(intent)
        } else {
            c.startService(intent)
        }
        c.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
}
