package com.esmailelhanash.flashlight.ui.permissions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PermissionRationaleDialog(onDismiss: () -> Unit) {
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