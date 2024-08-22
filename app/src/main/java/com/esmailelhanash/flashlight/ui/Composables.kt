package com.esmailelhanash.flashlight.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esmailelhanash.flashlight.R
import com.esmailelhanash.flashlight.cameramanager.turnOnFlashLight
import com.esmailelhanash.flashlight.ui.theme.FlashLightTheme


@Composable
fun Content(cameraPermissionState: PermissionState,componentActivity: ComponentActivity, checkCameraPermission: () -> Unit) {

    var isBackFlashlightOn by remember { mutableStateOf(false) }
    var backFlashMode  by remember { mutableStateOf(FlashMode.TORCH) }


    FlashLightTheme {
        Scaffold (
            topBar = { MainAppBar() }
        ){
            Surface(
                modifier = Modifier.padding(it).fillMaxSize(),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .background(
                                Color.Black
                                , shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .size(
                                100.dp, 100.dp
                            )
                            .clickable {
                                if (cameraPermissionState == PermissionState.GRANTED) {
                                    isBackFlashlightOn = !isBackFlashlightOn
                                    turnOnFlashLight(componentActivity)


                                }
                                else
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


                    // if back flash is on, show a horizontal selectable texts to
                    // choose between flash modes:
                    // torch, strobe, Pulse, SOS, Breathing modes, torch is default
                    if (isBackFlashlightOn) {
                        // a spacer
                        Spacer(modifier = Modifier.padding(20.dp))
                        FlashModesRow { flashMode ->
                            backFlashMode = flashMode
                        }
                    }

                }
            }
        }

    }

}
@Composable
fun FlashModesRow(onFlashModeSelected: (FlashMode) -> Unit) {
    var selectedFlashMode by remember { mutableStateOf(FlashMode.TORCH) }
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier.fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlashMode.entries.forEach { flashMode ->
            val isSelected = selectedFlashMode == flashMode
            val backgroundColor = if (isSelected) Color.Black else Color.White
            val textColor = if (isSelected) Color.White else Color.Black

            Box(
                modifier = Modifier
                    .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        selectedFlashMode = flashMode
                        onFlashModeSelected(flashMode)
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
    Content(
        PermissionState.GRANTED,
        ComponentActivity(),
    ){}
}


enum class FlashMode {
    TORCH,
    STROBE,
    PULSE,
    SOS,
    BREATHING
}