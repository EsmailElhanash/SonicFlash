package com.esmailelhanash.sonicflash.domain.flashlight

import com.esmailelhanash.sonicflash.domain.camera.ICameraProvider

abstract class IFlashLightController(cameraProvider: ICameraProvider) : FlashEffectExecute{
    val cameraManager = cameraProvider.cameraManager()
    val cameraId = cameraProvider.cameraId()
}