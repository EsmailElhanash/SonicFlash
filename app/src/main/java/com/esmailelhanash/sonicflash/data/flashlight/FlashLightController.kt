package com.esmailelhanash.sonicflash.data.flashlight

import com.esmailelhanash.sonicflash.data.model.FlashEffect
import com.esmailelhanash.sonicflash.domain.camera.ICameraProvider
import com.esmailelhanash.sonicflash.domain.flashlight.IFlashLightController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

class FlashLightController(cameraProvider: ICameraProvider) : IFlashLightController(cameraProvider = cameraProvider) {



    private var isRunning = false
    private lateinit var job : Job

    override fun executeFlashEffect(flashEffect: FlashEffect) {
        isRunning = true
        flashEffectLoop(flashEffect)
    }

    private fun flashEffectLoop(flashEffect: FlashEffect) {
        job = CoroutineScope(Dispatchers.IO).launch {
            turnOn()
            while (isRunning) {
                if (flashEffect.durationBetweenFlashPulsesInMilliSeconds != 0L) {
                    delay(flashEffect.durationOfFlashPulseInMilliSeconds)
                    turnOff()
                    delay(flashEffect.durationBetweenFlashPulsesInMilliSeconds)
                    turnOn()
                }
            }
            turnOff() // turn off flash
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