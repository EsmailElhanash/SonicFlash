package com.esmailelhanash.flashlight

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.esmailelhanash.flashlight.ui.Content

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            var shouldShowPermissionRationale by remember { mutableStateOf(false) }
            Content()

            if (shouldShowPermissionRationale){
                PermissionRationaleDialog{
                    shouldShowPermissionRationale = false

                    requestCameraPermission(
                        onPermissionDenied = {
                            shouldShowPermissionRationale = true
                        }
                    )

                }
            }


            requestCameraPermission(
                onPermissionDenied = {
                    shouldShowPermissionRationale = true
                }
            )
        }


    }

    @Composable
    private fun PermissionRationaleDialog(onDismiss: () -> Unit) {
        Dialog(
            onDismissRequest = {
                onDismiss()
            }
        ) {
            Column(
                // set background color to white:
                modifier = Modifier.padding(16.dp).background(color = Color.White),
            ) {
                Text(
                    "Camera permission is needed to use the flashlight",
                    modifier = Modifier.padding(16.dp)
                )
                // add a button to dismiss the dialog
                Text(
                    "OK",
                    modifier = Modifier.padding(16.dp)
                        .clickable {
                            onDismiss()
                        }
                )
            }

        }
    }

    // preview for permissionRationaleDialog
    @Composable
    @Preview(showBackground = true)
    private fun PermissionRationaleDialogPreview() {
        PermissionRationaleDialog(onDismiss = {})
    }

    private fun frontCamera() {
        val (hasFrontCamera, hasFrontFlash) = checkFrontCameraAndFlash(this)
        if (hasFrontCamera) {
            Log.d("CameraCheck", "Device has a front camera.")
            if (hasFrontFlash) {
                Log.d("CameraCheck", "Front camera has a flash.")
            } else {
                Log.d("CameraCheck", "Front camera does not have a flash.")
            }
        } else {
            Log.d("CameraCheck", "Device does not have a front camera.")
        }
    }

    private fun requestCameraPermission(
        onPermissionDenied: () -> Unit
    ) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Camera permission is not granted")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.CAMERA
                )
            ) {
                onPermissionDenied()
                // show dialog rationale
                // Log a message or handle the rationale for needing the permission
                Log.d(TAG, "Camera permission is needed to use the flashlight")
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    1
                )
            }
        }else {
            // todo start camera
        }
    }
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) { // Match the request code used in the requestPermissions call
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                Log.d(TAG, "Camera permission granted")
                startCamera() // You can start the camera if needed
            } else {
                // Permission was denied or request was cancelled
                Log.d(TAG, "Camera permission denied")
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                val imageCapture = ImageCapture.Builder()
                    .setFlashMode(ImageCapture.FLASH_MODE_ON)
                    .build()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, imageCapture)
                    .cameraControl
                    .enableTorch(true)
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }
}
