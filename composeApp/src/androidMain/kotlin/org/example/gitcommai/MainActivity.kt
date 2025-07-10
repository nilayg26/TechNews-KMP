package org.example.gitcommai
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidShared.sharedPreferences = this.getSharedPreferences("GitCommAIAndroid", Context.MODE_PRIVATE)
            val isDarkTheme = isSystemInDarkTheme()
            val backgroundColor = if (isDarkTheme) Color.Black else Color.White
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = backgroundColor.toArgb(),
                    darkScrim = backgroundColor.toArgb()
                ),
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = backgroundColor.toArgb(),
                    darkScrim = backgroundColor.toArgb()
                )
            )
            App()
        }
    }
}
object AndroidShared{
    lateinit var sharedPreferences:SharedPreferences
}

