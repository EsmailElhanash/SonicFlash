package com.esmailelhanash.sonicflash.presentation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionsHandler {
    private val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.FOREGROUND_SERVICE_CAMERA,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.FOREGROUND_SERVICE,)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
        )
    }

    @Composable
    fun RequestMissingPermissions(a: Activity, onAllPermissionsGranted: () -> Unit) {
        getMissingPermissions(a).let {
            val launcher = cameraPermissionLauncher(a){
                // if all requiredPermissions are granted
                if (checkPermissions(a))
                    onAllPermissionsGranted()
            }
            LaunchedEffect(launcher) {
                launcher.launch(it)
            }

        }
    }

    private fun getMissingPermissions(a: Activity) = requiredPermissions.filter {
        ContextCompat.checkSelfPermission(a, it) != PackageManager.PERMISSION_GRANTED
    }.toTypedArray()

    fun checkPermissions(a: Activity): Boolean
        = requiredPermissions.none {
        ContextCompat.checkSelfPermission(a, it) != PackageManager.PERMISSION_GRANTED
    }


    @Composable
    private fun cameraPermissionLauncher(
        context: Context,
        onPermissionsGranted: (permission: String) -> Unit
    ) = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { areGranted: Map<String, @JvmSuppressWildcards Boolean> ->
        areGranted.forEach { (permission, isGranted) ->

            if (!isGranted) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        permission
                    )
                ) {
                    Toast.makeText(
                        context,
                        "Camera permissions is needed to access the flashlight.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Camera Permissions Denied Permanently. Go to settings to enable it.",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
            } else {
                onPermissionsGranted(permission)
            }
        }
    }
}