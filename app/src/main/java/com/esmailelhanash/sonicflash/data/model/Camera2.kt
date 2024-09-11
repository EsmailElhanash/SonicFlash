package com.esmailelhanash.sonicflash.data.model

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.esmailelhanash.sonicflash.domain.camera.ICamera

@RequiresApi(Build.VERSION_CODES.M)
class Camera2(private val context: Context) : ICamera {
    private lateinit var cameraManager : CameraManager
    private lateinit var cameraId : String

    init {
        initializeCamera()
    }

    override fun initializeCamera() {
        cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager.cameraIdList.find { id ->
            cameraManager.getCameraCharacteristics(id).get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
        }.toString()
    }

    override fun destroyCamera() {

    }

    override fun turnOnFlash() {
        cameraManager.setTorchMode(cameraId, true)
    }

    override fun turnOffFlash() {
        cameraManager.setTorchMode(cameraId, false)
    }

}