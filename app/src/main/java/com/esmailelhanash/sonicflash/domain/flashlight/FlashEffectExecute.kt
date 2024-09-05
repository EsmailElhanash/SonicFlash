package com.esmailelhanash.sonicflash.domain.flashlight

import com.esmailelhanash.sonicflash.data.model.FlashEffect

interface FlashEffectExecute {

    fun executeFlashEffect(flashEffect: FlashEffect)
    fun stopFlashEffect()

}