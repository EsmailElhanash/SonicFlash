package com.esmailelhanash.sonicflash.data.model


data class FlashEffect (val name : String,
                        val durationBetweenFlashPulsesInMilliSeconds: Long
                        , val durationOfFlashPulseInMilliSeconds: Long)

private val TORCH = FlashEffect("TORCH",0, Long.MAX_VALUE)
private val STROBE = FlashEffect("STROBE",100,100)
private val PULSE = FlashEffect("PULSE",300,300)
private val SOS = FlashEffect("SOS",500,500)
private val BREATHING = FlashEffect("BREATHING",800,4800)

// list of the predefined flash modes
val defaultFlashModes = listOf(TORCH, STROBE, PULSE, SOS, BREATHING)