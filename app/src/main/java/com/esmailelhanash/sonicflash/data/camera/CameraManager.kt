package com.esmailelhanash.sonicflash.data.camera

import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleService
import com.esmailelhanash.sonicflash.domain.camera.ICameraProvider


class CameraManager(private val lifecycleService: LifecycleService)
    : ICameraProvider {


    private var cameraProvider : ProcessCameraProvider =
        ProcessCameraProvider.getInstance(lifecycleService).get()

    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private var imageCapture = ImageCapture.Builder()
        .setFlashMode(ImageCapture.FLASH_MODE_ON)
        .build()

    private var camera : Camera?  = null


    // constructor

    override fun getCamera() : Camera {
        if (camera != null) {
            return camera as Camera
        }

        camera = cameraProvider.bindToLifecycle(
            lifecycleService, cameraSelector, imageCapture
        )
        return camera as Camera
    }

    override fun destroyCamera() {
        camera?.cameraControl?.enableTorch(false)
        cameraProvider.unbindAll()
    }

}