package com.kappdev.wordbook.core.presentation.navigation

sealed class Screen(val route: String) {
    data object Collections: Screen("collections")
    data object Cards: Screen("cards")
    data object AddEditCollection: Screen("add-edit-collection")
}
