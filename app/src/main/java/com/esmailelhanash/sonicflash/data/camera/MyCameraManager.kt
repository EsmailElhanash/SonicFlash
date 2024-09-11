package com.esmailelhanash.sonicflash.data.camera

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleService
import com.esmailelhanash.sonicflash.domain.camera.ICameraProvider


class MyCameraManager(private val lifecycleService: LifecycleService)
    : ICameraProvider {


    private var cameraProvider : ProcessCameraProvider =
        ProcessCameraProvider.getInstance(lifecycleService).get()

//    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//    private var imageCapture = ImageCapture.Builder()
//        .setFlashMode(ImageCapture.FLASH_MODE_ON)
//        .build()

    private var cameraManager : CameraManager?  = null
    private var cameraId: String? = null

    override fun cameraId(): String {
        if  (cameraId != null) {
            return cameraId as String
        }
        val cameraManager = cameraManager()

        cameraId = cameraManager.cameraIdList.find { id ->
            cameraManager.getCameraCharacteristics(id)
                .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
        }
        return cameraId as String
    }


    // constructor

    override fun cameraManager() : CameraManager {
        if (cameraManager != null) {
            return cameraManager as CameraManager
        }

        cameraManager = lifecycleService.getSystemService(Context.CAMERA_SERVICE) as CameraManager

        return cameraManager as CameraManager

    }

    override fun destroyCamera() {
//        camera?.cameraControl?.enableTorch(false)
        cameraProvider.unbindAll()
    }

}