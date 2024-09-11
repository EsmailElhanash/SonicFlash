@file:Suppress("DEPRECATION")

package com.esmailelhanash.sonicflash.data.model

import android.hardware.Camera
import com.esmailelhanash.sonicflash.domain.camera.ICamera

class Camera1 : ICamera {

    private lateinit var camera1: Camera
    private lateinit var cameraParams : Camera.Parameters

    init {
        initializeCamera()
    }

    override fun initializeCamera() {
        camera1 = Camera.open()
        cameraParams = camera1.parameters
    }

    override fun destroyCamera() {

    }

    override fun turnOnFlash() {
        cameraParams.flashMode = Camera.Parameters.FLASH_MODE_TORCH
        camera1.startPreview()
    }

    override fun turnOffFlash() {
        cameraParams.flashMode = Camera.Parameters.FLASH_MODE_OFF
        camera1.stopPreview()

    }
}