package com.esmailelhanash.sonicflash.data.model

private val TORCH = FlashEffect("TORCH",Long.MAX_VALUE,0 )
private val STROBE = FlashEffect("STROBE",100,100)
private val PULSE = FlashEffect("PULSE",300,300)
private val SOS = FlashEffect("SOS",500,500)
private val BREATHING = FlashEffect("BREATHING",800,800)

private val TORCH_PATTERN = FlashPattern(
    "TORCH", listOf(TORCH),
    afterPatternBehaviour = AfterPatternBehaviour.KEEP_LAST_EFFECT
)

private val STROBE_PATTERN = FlashPattern(
    "STROBE", listOf(STROBE),
    afterPatternBehaviour = AfterPatternBehaviour.KEEP_LAST_EFFECT
)

private val PULSE_PATTERN = FlashPattern(
    "PULSE", listOf(PULSE),
    afterPatternBehaviour = AfterPatternBehaviour.KEEP_LAST_EFFECT
)

private val SOS_PATTERN = FlashPattern(
    "SOS", listOf(SOS),
    afterPatternBehaviour = AfterPatternBehaviour.KEEP_LAST_EFFECT
)

private val BREATHING_PATTERN = FlashPattern(
    "BREATHING", listOf(BREATHING),
    afterPatternBehaviour = AfterPatternBehaviour.KEEP_LAST_EFFECT
)

private val ASCENDING_PATTERN = FlashPattern(
    "ASCENDING", createAscendingList(500),
    afterPatternBehaviour = AfterPatternBehaviour.KEEP_LAST_EFFECT
)

private val DESCENDING_PATTERN = FlashPattern(
    "DESCENDING", List(5) { BREATHING } +
            List(5) { SOS } +
            List(5) { PULSE } +
            List(5) { STROBE } +
            List(5) { TORCH },
    afterPatternBehaviour = AfterPatternBehaviour.KEEP_LAST_EFFECT
)

private fun createAscendingList(count: Int) : List<FlashEffect> {
    return List(count) { STROBE.copy(pulseDuration = 10L + it * 1, afterPulseDuration = 10L + it * 1) }


}

val defaultFlashPatterns = listOf(
    TORCH_PATTERN, STROBE_PATTERN,
    PULSE_PATTERN, SOS_PATTERN, BREATHING_PATTERN,
    ASCENDING_PATTERN, DESCENDING_PATTERN
)