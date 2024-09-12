package com.esmailelhanash.sonicflash.data.model


// a Flash pattern consists of unlimited number of FlashEffects
data class FlashPattern(
    var name: String,
    var flashEffects: List<FlashEffect> = emptyList(),
    var afterPatternBehaviour : AfterPatternBehaviour
)

