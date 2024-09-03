package com.esmailelhanash.flashlight.domain.flashlight

import com.esmailelhanash.flashlight.data.model.FlashEffect

interface FlashEffectExecute {

    fun executeFlashEffect(flashEffect: FlashEffect)
    fun stopFlashEffect()

}