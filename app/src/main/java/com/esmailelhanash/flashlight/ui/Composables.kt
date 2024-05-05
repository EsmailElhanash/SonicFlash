package com.esmailelhanash.flashlight.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esmailelhanash.flashlight.R
import com.esmailelhanash.flashlight.ui.theme.FlashLightTheme
import com.esmailelhanash.flashlight.ui.theme.LightBlue


@Composable
fun Content() {
    FlashLightTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = LightBlue
        ) {
            FlashSwitch {

            }
        }
    }

}

@Composable
private fun FlashSwitch(onFlashToggle: (Boolean) -> Unit) {
    var isFrontFlash by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        IconButton(
            modifier = Modifier.size(96.dp).padding(16.dp),
            onClick = {
                isFrontFlash = !isFrontFlash
                onFlashToggle(isFrontFlash)
            }
        ) {
            Icon(
                painter = painterResource(id = if (isFrontFlash) R.drawable.flash_on else R.drawable.flash_off),
                contentDescription = if (isFrontFlash) "Front Flash" else "Back Flash",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            text = if (isFrontFlash) "Front Flash" else "Back Flash",
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentPreview() {
    Content()
}