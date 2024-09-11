package com.esmailelhanash.sonicflash.data.flashlight

import com.esmailelhanash.sonicflash.data.model.FlashEffect
import com.esmailelhanash.sonicflash.domain.camera.ICameraProvider
import com.esmailelhanash.sonicflash.domain.flashlight.IFlashLightController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

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
            try {
                while (isRunning) {
                    if (flashEffect.durationBetweenFlashPulsesInMilliSeconds != 0L) {
                        delayWithCancellation(flashEffect.durationOfFlashPulseInMilliSeconds)
                        turnOff()
                        delayWithCancellation(flashEffect.durationBetweenFlashPulsesInMilliSeconds)
                        turnOn()
                    }
                }
            } finally {
                turnOff() // Ensure the flashlight turns off when the loop ends
            }
        }
    }

    private suspend fun delayWithCancellation(timeMillis: Long) {
        suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                // Turn off the flashlight immediately on cancellation
                turnOff()
            }
            continuation.resumeWith(Result.success(Unit))
        }
        delay(timeMillis)
    }

    override fun stopFlashEffect() {
        isRunning = false
        job.cancel()
    }

    private fun turnOn() {
        cameraManager.setTorchMode(cameraId, true)
    }

    private fun turnOff() {
        cameraManager.setTorchMode(cameraId, false)
    }

}