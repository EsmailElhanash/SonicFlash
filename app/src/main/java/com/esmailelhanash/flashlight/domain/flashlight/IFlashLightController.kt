package com.esmailelhanash.flashlight.domain.flashlight

import com.esmailelhanash.flashlight.domain.camera.ICameraProvider

abstract class IFlashLightController(cameraProvider: ICameraProvider) : FlashEffectExecute{
    val camera = cameraProvider.getCamera()
}