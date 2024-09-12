package com.esmailelhanash.sonicflash.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esmailelhanash.sonicflash.data.model.FlashPattern
import com.esmailelhanash.sonicflash.domain.flashlight.IFlashPatternPlayer

class FlashlightViewModel(private val flashPatternPlayer: IFlashPatternPlayer) : ViewModel() {
    private var _isFlashLightOn : MutableLiveData<Boolean> = MutableLiveData()
    val isFlashLightOn : LiveData<Boolean> = _isFlashLightOn

    private var _flashPattern : MutableLiveData<FlashPattern> = MutableLiveData()
    val flashPattern : LiveData<FlashPattern> = _flashPattern
    //set flash effect
    fun setFlashPattern(flashPattern: FlashPattern) {
        _flashPattern.value = flashPattern
    }
    //set flashlight
    fun toggleFlash() {
        val currentValue = _isFlashLightOn.value
        // trigger the value
        _isFlashLightOn.value = if (currentValue == null) true else !currentValue
        // get the value
        // execute the effect
        isFlashLightOn.value?.let { executePattern(it) }
    }

    private fun executePattern(isFlashLightOn: Boolean) {
        if (isFlashLightOn) {
            flashPatternPlayer.playPattern(_flashPattern.value!!)
        } else {
            flashPatternPlayer.stopPattern()
        }
    }


}

// factory for the viewmodel
class FlashlightViewModelFactory(private val flashPatternPlayer: IFlashPatternPlayer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FlashlightViewModel(flashPatternPlayer) as T
    }
}