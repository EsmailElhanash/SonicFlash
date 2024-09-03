package com.esmailelhanash.flashlight.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.esmailelhanash.flashlight.R
import com.esmailelhanash.flashlight.data.model.FlashEffect
import com.esmailelhanash.flashlight.data.model.defaultFlashModes
import com.esmailelhanash.flashlight.presentation.ui.theme.FlashLightTheme
import com.esmailelhanash.flashlight.service.FlashlightService


@Composable
fun MainContent(cameraPermissionState: PermissionState,
                lifecycleService: FlashlightService?,
                checkCameraPermission: () -> Unit) {

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
                    Box{
                        CircularProgressIndicator()
                    }
                }else MainColumnContent(cameraPermissionState, lifecycleService, checkCameraPermission)

            }
        }

    }

}
@Composable
private fun MainColumnContent(
    cameraPermissionState: PermissionState,
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
                    if (cameraPermissionState == PermissionState.GRANTED) {

                    } else
                        checkCameraPermission()
                    // handle back flash
                }
        ) {

        }

    }
}

@Composable
private fun FlashModesRow(onFlashModeSelected: (FlashEffect) -> Unit) {
//    var selectedFlashMode by remember { mutableStateOf(FlashEffect.TORCH) }
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier.fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        defaultFlashModes.forEach { flashMode ->
//            val isSelected = selectedFlashMode == flashMode
//            val backgroundColor = if (isSelected) Color.Black else Color.White
//            val textColor = if (isSelected) Color.White else Color.Black

            Box(
                modifier = Modifier
//                    .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                    .clickable {
//                        selectedFlashMode = flashMode
                        onFlashModeSelected(flashMode)
                    }
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    flashMode.name,
//                    color = textColor,
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


//@Preview(showBackground = true)
//@Composable
//private fun ContentPreview() {
//    MainContent(
//        PermissionState.GRANTED
//    ){}
//}


