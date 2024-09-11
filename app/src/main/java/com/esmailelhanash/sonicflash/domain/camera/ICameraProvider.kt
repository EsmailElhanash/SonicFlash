package com.esmailelhanash.sonicflash.domain.camera

import android.hardware.camera2.CameraManager


interface ICameraProvider {
    fun cameraId(): String

    fun cameraManager(): CameraManager

    fun destroyCamera()
}