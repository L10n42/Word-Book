package com.kappdev.wordbook.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kappdev.wordbook.core.presentation.navigation.NavConst.CARD_ID
import com.kappdev.wordbook.core.presentation.navigation.NavConst.COLLECTION_ID
import com.kappdev.wordbook.main_feature.presentation.add_edit_card.components.AddEditCardScreen
import com.kappdev.wordbook.main_feature.presentation.add_edit_collection.components.AddEditCollectionScreen
import com.kappdev.wordbook.main_feature.presentation.cards.components.CardsScreen
import com.kappdev.wordbook.main_feature.presentation.collections.components.CollectionsScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Collections.route
    ) {
        composable(
            Screen.Collections.route
        ) {
            CollectionsScreen(navController)
        }

        composable(
            route = Screen.Cards.route + "$COLLECTION_ID={$COLLECTION_ID}",
            arguments = listOf(
                navArgument(COLLECTION_ID) { type = NavType.IntType; defaultValue = -1 }
            )
        ) {
            val collectionId = it.arguments?.getInt(COLLECTION_ID)
            CardsScreen(navController, collectionId)
        }

        composable(
            route = Screen.AddEditCollection.route + "?$COLLECTION_ID={$COLLECTION_ID}",
            arguments = listOf(
                navArgument(COLLECTION_ID) { type = NavType.IntType; defaultValue = -1 }
            )
        ) {
            val collectionId = it.arguments?.getInt(COLLECTION_ID)
            AddEditCollectionScreen(collectionId, navController)
        }

        composable(
            route = Screen.AddEditCard.route + "?$COLLECTION_ID={$COLLECTION_ID}&$CARD_ID={$CARD_ID}",
            arguments = listOf(
                navArgument(COLLECTION_ID) { type = NavType.IntType; defaultValue = -1 },
                navArgument(CARD_ID) { type = NavType.IntType; defaultValue = -1 }
            )
        ) {
            val collectionId = it.arguments?.getInt(COLLECTION_ID)
            val cardId = it.arguments?.getInt(CARD_ID)
            AddEditCardScreen(navController, collectionId, cardId)
        }
    }
}