package com.esmailelhanash.flashlight.cameramanager

import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat


fun turnOnFlashLight(componentActivity : ComponentActivity) {
    componentActivity.apply {
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
                    this, cameraSelector, imageCapture
                )
                    .cameraControl
                    .enableTorch(true)
            } catch (exc: Exception) {
//                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

}