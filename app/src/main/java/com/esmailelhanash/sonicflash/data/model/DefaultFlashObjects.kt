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
    "ASCENDING", generateFlashEffectList(500),
    afterPatternBehaviour = AfterPatternBehaviour.STOP
)

private val DESCENDING_PATTERN = FlashPattern(
    "DESCENDING", generateFlashEffectList(500).reversed(),
    afterPatternBehaviour = AfterPatternBehaviour.STOP
)

fun generateFlashEffectList(
    count: Int,
    initialDuration: Long = 10,
    increaseAmount: Long = 5
): List<FlashEffect> {
    return List(count) { index ->
        FlashEffect(
            name = "Effect ${index + 1}",
            pulseDuration = initialDuration + index * increaseAmount,
            afterPulseDuration = initialDuration + index * increaseAmount
        )
    }
}

val defaultFlashPatterns = listOf(
    TORCH_PATTERN, STROBE_PATTERN,
    PULSE_PATTERN, SOS_PATTERN, BREATHING_PATTERN,
    ASCENDING_PATTERN, DESCENDING_PATTERN
)