package com.esmailelhanash.sonicflash.presentation.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esmailelhanash.sonicflash.R
import com.esmailelhanash.sonicflash.data.model.defaultFlashModes
import com.esmailelhanash.sonicflash.presentation.ui.theme.FlashLightTheme
import com.esmailelhanash.sonicflash.service.FlashlightService


@Composable
fun MainContent(permissionsGranted: Boolean,
                lifecycleService: FlashlightService?,

                checkPermissions: () -> Unit) {

    FlashLightTheme {
        Scaffold (
            topBar = { MainAppBar() }
        ){
            Surface(
                modifier = Modifier.padding(it).fillMaxSize(),
                color = Color.White
            ) {

                if (lifecycleService == null) {
                    // show some loading indicator
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }else MainColumnContent(permissionsGranted, lifecycleService, checkPermissions)

            }
        }

    }

}
@Composable
private fun MainColumnContent(
    permissionsGranted: Boolean,
    lifecycleService: FlashlightService,
    checkCameraPermission: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isBackFlashlightOn = lifecycleService.isFlashOn().observeAsState().value ?: false
        Box(
            modifier = Modifier
                .background(
                    Color.Black, shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .size(
                    100.dp, 100.dp
                )
                .clickable {
                    if (permissionsGranted) {
                        Log.d("buttonTabTime", System.currentTimeMillis().toString())

                        lifecycleService.toggleFlash()
                    } else
                        checkCameraPermission()
                    // handle back flash
                }
        ) {
            // an image, showing flash_on.xml if flash light is on, and showing
            // flash_off.xml if flash light is off
            Icon(
                painter = if (isBackFlashlightOn) painterResource(id = R.drawable.flash_on) else painterResource(id = R.drawable.flash_off),
                contentDescription = "Flashlight",
                tint = Color.White,

            )
        }

        // a 40 db space
        Box(
            modifier = Modifier
                .padding(20.dp)
        )

        // a row of flash modes
        FlashModesRow(lifecycleService)


        // a 40 db space
        Box(
            modifier = Modifier
                .padding(40.dp)
        )

        Box(
            modifier = Modifier.height(300.dp)
        ){ AdContent() }

    }
}





@Composable
private fun FlashModesRow(lifecycleService: FlashlightService) {
    val flashEffect = lifecycleService.getFlashEffect().observeAsState().value
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier.fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        defaultFlashModes.forEach { flashMode ->
            val isSelected = flashEffect == flashMode
            val backgroundColor = if (isSelected) Color.Black else Color.White
            val textColor = if (isSelected) Color.White else Color.Black

            Box(
                modifier = Modifier
                    .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        lifecycleService.setFlashEffect(flashMode)
                    }
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    flashMode.name,
                    color = textColor,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun MainAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.Black
            )
            .padding(horizontal = 24.dp, vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start

    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Flashlight",
            tint = Color.White,
            modifier = Modifier.size(100.dp)
        )
        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            // a title
            Text(
                text = "Flashlight",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ContentPreview() {
    MainContent(
        true,
        FlashlightService()
    ){}
}


