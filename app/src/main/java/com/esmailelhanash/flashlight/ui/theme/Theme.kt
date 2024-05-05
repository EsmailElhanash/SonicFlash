package com.esmailelhanash.flashlight.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = LightBlue, // Using Bright Yellow as the primary color.
    onPrimary = IconWhite, // Using White for content/text that is on top of the primary color.
    secondary = ElementDarkGray, // Using Dark Gray as the secondary color.
    onSecondary = IconWhite, // Using White for content/text that is on top of the secondary color.
    tertiary = ElementDarkGray, // Using Dark Gray again for the tertiary, but you could choose another accent color if you wanted.
    onTertiary = IconWhite, // Using White for content/text that is on top of the tertiary color.
    background = LightBlue, // Using Bright Yellow as the background color.
    onBackground = ElementDarkGray, // Using Dark Gray for content/text on the background.
    surface = IconWhite, // Optional, if you need a surface color, you might want to use White.
    onSurface = ElementDarkGray, // Using Dark Gray for content/text on the surface color.

    // You can continue to override other default colors as needed
)


@Composable
fun FlashLightTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}