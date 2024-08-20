package com.esmailelhanash.flashlight.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.sp
import com.esmailelhanash.flashlight.R
import com.esmailelhanash.flashlight.ui.theme.FlashLightTheme
import com.esmailelhanash.flashlight.ui.theme.LightBlue
import com.esmailelhanash.flashlight.ui.theme.LighterBlue


@Composable
fun Content(cameraPermissionState: PermissionState, checkCameraPermission: () -> Unit) {

    var isBackFlashlightOn by remember { mutableStateOf(false) }
    var isFrontFlashlightOn by remember { mutableStateOf(false) }


    var backFlashMode  by remember { mutableStateOf(FlashMode.TORCH) }
    var frontFlashMode by remember { mutableStateOf(FlashMode.TORCH) }

    // if the flash is on then the bg color is white else it is black
    // and the opposite for the text color and the same for front flash

    val backgroundColorBackFlash = if (isBackFlashlightOn || isFrontFlashlightOn) Color.White else Color.Black
    val textColorBackFlash = if (isBackFlashlightOn || isFrontFlashlightOn) Color.Black else Color.White

    val backgroundColorFrontFlash = if (isFrontFlashlightOn) Color.White else Color.Black
    val textColorFrontFlash = if (isFrontFlashlightOn) Color.Black else Color.White

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

                    // two big buttons , the first is for back flash and the second is for front flash
                    // with full width and height and background color and text color and rounded corners
                    // and onClick listeners to handle the flash direction changes
                    Box(
                        modifier = Modifier
                            .background(backgroundColorBackFlash, shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 64.dp, vertical = 16.dp)
                            .clickable {
                                if (cameraPermissionState == PermissionState.GRANTED)
                                    isBackFlashlightOn = !isBackFlashlightOn
                                else
                                    checkCameraPermission()
                                // handle back flash
                            }
                    ) {
                        Text(
                            text = "Back Flash",
                            color = textColorBackFlash,
                            fontSize = 20.sp, // Set the desired font size
                            modifier = Modifier
                                .align(Alignment.Center) // Center the text within the Box
                        )
                    }


                    // if back flash is on, show a horizontal selectable texts to
                    // choose between flash modes:
                    // torch, strobe, Pulse, SOS, Breathing modes, torch is default
                    if (isBackFlashlightOn)
                        FlashModesRow{ flashMode ->
                            backFlashMode = flashMode
                        }

                    Spacer(
                        modifier = Modifier.padding(8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .background(backgroundColorFrontFlash, shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 64.dp, vertical = 16.dp)
                            .clickable {
                                if (cameraPermissionState == PermissionState.GRANTED)
                                    isFrontFlashlightOn = !isFrontFlashlightOn
                                else
                                    checkCameraPermission()

                            }
                    ) {
                        Text(
                            text = "Front Flash",
                            color = textColorFrontFlash,
                            fontSize = 20.sp, // Set the desired font size
                            modifier = Modifier
                                .align(Alignment.Center) // Center the text within the Box
                        )
                    }

                    if (isFrontFlashlightOn)
                        FlashModesRow { flashMode ->
                            frontFlashMode = flashMode
                        }


                    // a button to request camera permission

                }
            }
        }

    }

}



// flash modes row composable
// show a horizontal selectable texts to
// choose between flash modes:
// torch, strobe, Pulse, SOS, Breathing modes, torch is default
@Composable
fun FlashModesRow(onFlashModeSelected: (FlashMode) -> Unit) {
    var selectedFlashMode by remember { mutableStateOf(FlashMode.TORCH) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, LightBlue, shape = RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlashMode.entries.forEach { flashMode ->
            Button(
                onClick = {
                    selectedFlashMode = flashMode
                    onFlashModeSelected(flashMode)
                },
                modifier = Modifier
                    .padding(8.dp)
                    .background(if (selectedFlashMode == flashMode) LighterBlue else Color.Transparent, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = flashMode.name,
                    color = if (selectedFlashMode == flashMode) Color.White else Color.Black
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
        PermissionState.GRANTED
    ){}
}


enum class FlashMode {
    TORCH,
    STROBE,
    PULSE,
    SOS,
    BREATHING
}