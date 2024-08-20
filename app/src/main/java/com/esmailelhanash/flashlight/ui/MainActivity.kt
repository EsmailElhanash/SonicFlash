package com.esmailelhanash.flashlight.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val context = LocalContext.current

            // Define the permission you want to request
            val cameraPermission = Manifest.permission.CAMERA

            // Define the launcher for the permission request
            val launcher = cameraPermissionLauncher(context)

            // Check the current permission status
            val hasCameraPermission = ContextCompat.checkSelfPermission(
                context, cameraPermission
            ) == PackageManager.PERMISSION_GRANTED


            var cameraPermissionState by remember { mutableStateOf(PermissionState.DENIED) }

            Content(cameraPermissionState){
                if (!hasCameraPermission) {
                    launcher.launch(cameraPermission)
                }else{
                    cameraPermissionState = PermissionState.GRANTED
                }
            }
        }
    }

    @Composable
    private fun cameraPermissionLauncher(
        context: Context,
    ) = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.CAMERA
                )
            ) {
                Toast.makeText(
                    context,
                    "Camera permission is needed to access the flashlight.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Camera Permission Denied Permanently. Go to settings to enable it.",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }
        }
    }
}
enum class PermissionState {
    GRANTED, DENIED,
}
