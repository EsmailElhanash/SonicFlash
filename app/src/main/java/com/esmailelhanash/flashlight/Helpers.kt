package com.esmailelhanash.flashlight

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log

fun checkFrontCameraAndFlash(context: Context): Pair<Boolean, Boolean> {
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    var hasFrontCamera = false
    var hasFrontFlash = false

    try {
        for (cameraId in cameraManager.cameraIdList) {
            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
            val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
            val flashAvailable = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) ?: false

            if (facing == CameraCharacteristics.LENS_FACING_FRONT) {
                hasFrontCamera = true
                if (flashAvailable) {
                    hasFrontFlash = true
                }
                break // Exit the loop once the front camera is found
            }
        }
    } catch (e: CameraAccessException) {
        Log.e("CameraAccess", "Failed to access Camera", e)
    }

    return Pair(hasFrontCamera, hasFrontFlash)
}