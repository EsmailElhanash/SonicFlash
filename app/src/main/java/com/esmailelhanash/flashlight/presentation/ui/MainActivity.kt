package com.esmailelhanash.flashlight.presentation.ui

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.esmailelhanash.flashlight.service.FlashlightService

class MainActivity : ComponentActivity() {
    private val flashlightServiceState = mutableStateOf<FlashlightService?>(null)

    private val cameraPermission = Manifest.permission.CAMERA
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

        setContent {
            var cameraPermissionState by remember { mutableStateOf(PermissionState.DENIED) }
            val hasCameraPermission = isCameraPermissionGranted()

            val launcher = cameraPermissionLauncher(LocalContext.current)
            MainContent(cameraPermissionState,flashlightServiceState.value){
                if (!hasCameraPermission) {
                    launcher.launch(cameraPermission)
                }else{
                    cameraPermissionState = PermissionState.GRANTED
                }
            }
        }
    }

    @Composable
    private fun isCameraPermissionGranted() : Boolean {
        val context = LocalContext.current

        return ContextCompat.checkSelfPermission(
            context, cameraPermission
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun startFlashlightService(context: Context) {
        val intent = Intent(context, FlashlightService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun stopFlashlightService(context: Context) {
        val intent = Intent(context, FlashlightService::class.java)
        context.stopService(intent)
    }

    override fun onStart() {
        super.onStart()
        startFlashlightService(this)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
    @Composable
    private fun cameraPermissionLauncher(
        context: Context,
    ) = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.CAMERA
                )
            ) {
                Toast.makeText(
                    context,
                    "Camera permission is needed to access the flashlight.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Camera Permission Denied Permanently. Go to settings to enable it.",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }
        }
    }
}
enum class PermissionState {
    GRANTED, DENIED,
}
