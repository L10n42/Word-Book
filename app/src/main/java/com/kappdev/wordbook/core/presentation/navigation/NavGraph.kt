package com.kappdev.wordbook.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            Screen.Cards.route
        ) {
            CardsScreen(navController)
        }

        composable(
            Screen.AddEditCollection.route.arg(COLLECTION_ID, true),
            arguments = navArguments(NavArgument(COLLECTION_ID, NavType.IntType, defaultValue = -1))
        ) {
            val collectionId = it.arguments?.getInt(COLLECTION_ID)
            AddEditCollectionScreen(collectionId, navController)
        }

        composable(
            Screen.AddEditCard.route
        ) {
            AddEditCardScreen(navController)
        }
    }
}

private data class NavArgument(
    val name: String,
    val type: NavType<*>,
    val defaultValue: Any? = null
)

private fun navArguments(vararg arguments: NavArgument): List<NamedNavArgument> {
    return arguments.map { arg ->
        navArgument(arg.name) {
            type = arg.type
            defaultValue = arg.defaultValue
        }
    }
}

/**
 * Appends a navigation argument to the route with the provided name.
 * This function is used to construct a route with a named argument.
 *
 * @param name The name of the argument.
 * @return A string with the appended argument in the format "/{name}".
 */
private fun String.arg(name: String, optional: Boolean = false): String {
    val prefix = if (optional) "?" else ""
    return this.plus("$prefix$name={$name}")
}
