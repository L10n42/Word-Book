package com.kappdev.wordbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kappdev.wordbook.core.presentation.navigation.NavGraph
import com.kappdev.wordbook.ui.theme.WordBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordBookTheme(darkTheme = false) {
                val navController = rememberNavController()
                val systemUiController = rememberSystemUiController()
                val surfaceColor = MaterialTheme.colorScheme.surface

                SideEffect {
                    systemUiController.setSystemBarsColor(surfaceColor)
                }

                NavGraph(navController)
            }
        }
    }
}