package com.esmailelhanash.flashlight.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esmailelhanash.flashlight.data.model.FlashEffect
import com.esmailelhanash.flashlight.domain.flashlight.FlashEffectExecute

class FlashlightViewModel(private val flashEffectExecute: FlashEffectExecute) : ViewModel() {
    private var _isFlashLightOn : MutableLiveData<Boolean> = MutableLiveData()
    //val isFlashLightOn : LiveData<Boolean> = _isFlashLightOn

    private var _flashEffect : MutableLiveData<FlashEffect> = MutableLiveData()
    //val flashEffect : LiveData<FlashEffect> = _flashEffect
    //set flash effect
    fun setFlashEffect(flashEffect: FlashEffect) {
        _flashEffect.value = flashEffect
    }
    //set flashlight
    fun setFlashLight(isFlashLightOn: Boolean) {
        _isFlashLightOn.value = isFlashLightOn
        executeEffect(isFlashLightOn)
    }

    private fun executeEffect(isFlashLightOn: Boolean) {
        if (isFlashLightOn) {
            flashEffectExecute.executeFlashEffect(_flashEffect.value!!)
        } else {
            flashEffectExecute.stopFlashEffect()
        }
    }


}

// factory for the viewmodel
class FlashlightViewModelFactory(private val flashEffectExecute: FlashEffectExecute) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FlashlightViewModel(flashEffectExecute) as T
    }
}