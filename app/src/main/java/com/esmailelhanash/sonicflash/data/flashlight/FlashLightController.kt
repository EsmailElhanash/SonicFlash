package com.esmailelhanash.sonicflash.data.flashlight

import com.esmailelhanash.sonicflash.data.model.FlashEffect
import com.esmailelhanash.sonicflash.domain.camera.ICameraProvider
import com.esmailelhanash.sonicflash.domain.flashlight.IFlashLightController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FlashLightController(cameraProvider: ICameraProvider) : IFlashLightController(cameraProvider = cameraProvider) {



    private var isRunning = false

    override fun executeFlashEffect(flashEffect: FlashEffect) {
        isRunning = true
        flashEffectLoop(flashEffect)
    }

    private fun flashEffectLoop(flashEffect: FlashEffect) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            while (isRunning) {
                turnOn()
                if (flashEffect.durationBetweenFlashPulsesInMilliSeconds != 0L) {
                    delay(flashEffect.durationOfFlashPulseInMilliSeconds)
                    turnOff()
                    delay(flashEffect.durationBetweenFlashPulsesInMilliSeconds)
                }
            }
        }
        job.start()
    }

    override fun stopFlashEffect() {
        isRunning = false
    }

    private fun turnOn() {
        camera.cameraControl.enableTorch(true) // turn on flash
    }

    private fun turnOff() {
        camera.cameraControl.enableTorch(false) // turn off flash
    }

}