package com.esmailelhanash.sonicflash.data.model

import android.content.Context
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import com.esmailelhanash.sonicflash.domain.camera.ICamera

class CameraX(private val context: Context) : ICamera {

    private val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    private lateinit var cameraProvider  : ProcessCameraProvider
    private lateinit var cameraSelector : CameraSelector
    private var imageCapture = ImageCapture.Builder()
        .setFlashMode(ImageCapture.FLASH_MODE_ON)
        .build()
    private lateinit var camera : Camera

    init {
        initializeCamera()
    }

    override fun initializeCamera() {
        cameraProvider = cameraProviderFuture.get()
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        camera = cameraProvider.bindToLifecycle(context as LifecycleOwner, cameraSelector, imageCapture)
    }

    override fun destroyCamera() {
        TODO("Not yet implemented")
    }

    override fun turnOnFlash() {
        camera.cameraControl.enableTorch(true)
    }

    override fun turnOffFlash() {
        camera.cameraControl.enableTorch(false)
    }
}