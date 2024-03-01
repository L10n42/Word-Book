package com.kappdev.wordbook.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kappdev.wordbook.main_feature.presentation.collections.components.CollectionsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Collections.route
    ) {
        composable(
            Screen.Collections.route
        ) {
            CollectionsScreen()
        }
    }
}