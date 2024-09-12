package com.esmailelhanash.sonicflash.data.flashlight

import com.esmailelhanash.sonicflash.data.model.AfterPatternBehaviour
import com.esmailelhanash.sonicflash.data.model.FlashEffect
import com.esmailelhanash.sonicflash.data.model.FlashPattern
import com.esmailelhanash.sonicflash.domain.camera.ICameraToggle
import com.esmailelhanash.sonicflash.domain.flashlight.IFlashPatternPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine

class FlashPatternPlayer(private val flashToggle: ICameraToggle) : IFlashPatternPlayer {
    private var isRunning = false
    private lateinit var job : Job


    override fun playPattern(flashPattern: FlashPattern) {
        if (isRunning) {
            stopPattern()
        }
        isRunning = true
        play(flashPattern)
    }

    private fun play(flashPattern: FlashPattern){
        job = CoroutineScope(Dispatchers.IO).launch {
            afterPatternWrapper(flashPattern){
                for (flashEffect in flashPattern.flashEffects) {
                    runBlocking {
                        flashEffectPlayer(flashEffect)
                    }
                }
            }
            stopPattern()
        }
        job.start()
    }

    private suspend fun afterPatternWrapper(flashPattern: FlashPattern,
                                            pattern: () -> Unit){

                    when(flashPattern.afterPatternBehaviour){
                        AfterPatternBehaviour.REPEAT_PATTERN -> {
                            while (isRunning){
                                pattern()
                            }
                        }
                        AfterPatternBehaviour.KEEP_LAST_EFFECT -> {
                            pattern()
                            while (isRunning){
                                flashEffectPlayer(flashPattern.flashEffects.last())
                            }
                        }
                        AfterPatternBehaviour.STOP -> {
                            pattern()
                        }
                    }
    }







    override fun stopPattern() {
        isRunning = false
        job.cancel()
        flashToggle.turnOffFlash()
    }

    private suspend fun flashEffectPlayer(flashEffect: FlashEffect) {
        if (!isRunning) return
        flashToggle.turnOnFlash()
        if (flashEffect.afterPulseDuration != 0L) {
            delayWithCancellation(flashEffect.pulseDuration)
            flashToggle.turnOffFlash()
            delayWithCancellation(flashEffect.afterPulseDuration)
            flashToggle.turnOnFlash()
        }
    }

    private suspend fun delayWithCancellation(timeMillis: Long) {
        suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                // Turn off the flashlight immediately on cancellation
                flashToggle.turnOffFlash()
            }
            continuation.resumeWith(Result.success(Unit))
        }
        delay(timeMillis)
    }
}