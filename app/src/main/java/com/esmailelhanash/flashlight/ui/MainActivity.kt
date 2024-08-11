package com.esmailelhanash.flashlight.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.esmailelhanash.flashlight.ui.permissions.PermissionHandlers

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Content()
            PermissionHandlers(activity = this)
        }


    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) { // Match the request code used in the requestPermissions call
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                Log.d(TAG, "Camera permission granted")

            } else {
                // Permission was denied or request was cancelled
                Log.d(TAG, "Camera permission denied")
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                val imageCapture = ImageCapture.Builder()
                    .setFlashMode(ImageCapture.FLASH_MODE_ON)
                    .build()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, imageCapture)
                    .cameraControl
                    .enableTorch(true)
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }
}
