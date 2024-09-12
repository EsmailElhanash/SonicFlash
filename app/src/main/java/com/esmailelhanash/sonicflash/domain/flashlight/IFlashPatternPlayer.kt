package com.esmailelhanash.sonicflash.domain.flashlight

import com.esmailelhanash.sonicflash.data.model.FlashPattern

interface IFlashPatternPlayer {

    fun playPattern(flashPattern: FlashPattern)
    fun stopPattern()

}