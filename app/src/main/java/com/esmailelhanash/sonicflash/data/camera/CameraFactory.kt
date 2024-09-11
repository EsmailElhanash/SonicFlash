package com.esmailelhanash.sonicflash.data.camera


import android.os.Build
import androidx.lifecycle.LifecycleService
import com.esmailelhanash.sonicflash.data.model.Camera2
import com.esmailelhanash.sonicflash.data.model.CameraX


object CameraFactory {
    fun getCamera(lifecycleService: LifecycleService) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        Camera2(lifecycleService)
    else CameraX(lifecycleService)
}