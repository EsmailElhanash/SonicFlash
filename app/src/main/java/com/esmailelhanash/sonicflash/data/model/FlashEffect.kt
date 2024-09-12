package com.esmailelhanash.sonicflash.data.model


data class FlashEffect (val name : String
                        , val pulseDuration: Long,
                        val afterPulseDuration: Long,
    ){
    companion object {
        fun createList(name: String, pulseDuration: Long, afterPulseDuration: Long,count:Int) : List<FlashEffect> {

            val list = mutableListOf<FlashEffect>()
            for (i in 0 until count) {
                list.add(FlashEffect(name, pulseDuration, afterPulseDuration))
            }
            return list

        }

        fun createList(flashEffect: FlashEffect, count:Int) : List<FlashEffect> {
            val list = mutableListOf<FlashEffect>()
            for (i in 0 until count) {
                list.add(flashEffect)
            }
            return list

        }
    }
}


