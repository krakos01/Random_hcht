package com.app.randomhcht.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    onPrimary = Color(0xFF232e3e),
    primary = Color(0xff9aacc6),
    secondary = Color(0xffc6b49a),
    tertiary = Color(0xFFc69aac),

  /*  background = Color(0xFF000d1e),
    surface = Color(0xFF000d1e),
    onBackground = Color(0xFFFFFBFE),
    onSurface = Color(0xFFe4e8ee),
    onPrimary = Color(0xFFe4e8ee),
    onSecondary = Color.White,
    onTertiary = Color(0xFFe4e8ee),
    primaryContainer = Color(0xFF232e3e),*/
)

private val LightColorScheme = lightColorScheme(
    primary =  Color(0xFF232e3e),
    secondary = Color(0xff3e3323),
    tertiary = Color(0xff4f2f3a),

/*    background =  Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    primaryContainer = Color(0xffe4f1ff),*/

)

@Composable
fun RandomHchtTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    if(darkTheme){
        systemUiController.setSystemBarsColor(
            color = colorScheme.background
        )
    }else{
        systemUiController.setSystemBarsColor(
            color = colorScheme.background
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
