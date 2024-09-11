package com.esmailelhanash.sonicflash.domain.camera


interface ICameraProvider {

    fun initializeCamera()

    fun destroyCamera()
}