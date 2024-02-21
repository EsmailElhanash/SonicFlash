package com.esmailelhanash.flashlight


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.esmailelhanash.flashlight.ui.theme.BrightYellow
import com.esmailelhanash.flashlight.ui.theme.FlashLightTheme


class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashLightTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BrightYellow
                ) {
                    Content()
                }
            }
        }


        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // Preview
//            val preview = CameraPreview.Builder()
//                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, null)
                    .cameraControl.enableTorch(true)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

}

@Composable
fun Content() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        FlashSwitch{}
        Text(text = "Hello World!", style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
fun FlashSwitch(onFlashToggle: (Boolean) -> Unit) {
    var isFrontFlash by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                isFrontFlash = !isFrontFlash
                onFlashToggle(isFrontFlash)
            }
        ) {
            // show some image with drawable flash_off which is an svg and saved as XML by default
            Image(
                modifier = Modifier
                    .size(50.dp) // Adjust size as needed
                    .padding(16.dp),
                painter = rememberVectorPainter(
                    ImageVector.vectorResource(R.drawable.flash_off)
                ),
                contentDescription = "My SVG Image"
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
fun ContentPreview() {
    FlashLightTheme {
        Content()
    }
}