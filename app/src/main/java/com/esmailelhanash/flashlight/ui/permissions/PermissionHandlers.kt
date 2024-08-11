package com.esmailelhanash.flashlight.ui.permissions

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


private const val REQUEST_CODE_CAMERA_PERMISSION = 10201
@Composable
fun PermissionHandlers(activity: Activity) {
    val hasCameraPermission by remember { mutableStateOf(
        ContextCompat.checkSelfPermission(
            activity,
            android.Manifest.permission.CAMERA
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    ) }

    var showRationaleDialog by remember { mutableStateOf(
        ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            android.Manifest.permission.CAMERA)

    ) }




    if (!hasCameraPermission) {
        if (showRationaleDialog) {
            PermissionRationaleDialog onDismiss@{
                showRationaleDialog = false
                requestCameraPermission(
                    activity = activity,
                )
            }
        } else {
            requestCameraPermission(
                activity = activity
            )
        }
    }
}
private fun requestCameraPermission(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(android.Manifest.permission.CAMERA),
        REQUEST_CODE_CAMERA_PERMISSION
    )
}