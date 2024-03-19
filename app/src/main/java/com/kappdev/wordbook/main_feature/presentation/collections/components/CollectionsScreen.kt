package com.kappdev.wordbook.main_feature.presentation.collections.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.common.AlertSheet
import com.kappdev.wordbook.core.presentation.navigation.NavConst
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.core.presentation.navigation.putArg
import com.kappdev.wordbook.main_feature.presentation.collections.CollectionSheet
import com.kappdev.wordbook.main_feature.presentation.collections.CollectionsViewModel
import com.kappdev.wordbook.main_feature.presentation.common.Option
import com.kappdev.wordbook.main_feature.presentation.common.components.AnimatedFAB

@Composable
fun CollectionsScreen(
    navController: NavHostController,
    viewModel: CollectionsViewModel = hiltViewModel()
) {
    val (collectionSheet, openSheet) = remember { mutableStateOf<CollectionSheet?>(null) }

    if (collectionSheet != null) {
        CollectionSheetHandler(collectionSheet, viewModel, navController::navigate, openSheet)
    }

    LaunchedEffect(Unit) {
        viewModel.getCollections()
    }

    Scaffold(
        topBar = {
            CollectionsTopBar()
        },
        floatingActionButton = {
            AnimatedFAB(text = stringResource(R.string.btn_new), icon = Icons.Rounded.Add) {
                navController.navigate(Screen.AddEditCollection.route)
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { pv ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.collections, { it.id }) { collectionInfo ->

                CollectionCard(
                    info = collectionInfo,
                    cardMoreOpened = (collectionSheet is CollectionSheet.Options && collectionSheet.collection == collectionInfo),
                    modifier = Modifier.fillMaxWidth(),
                    onNewCard = {
                        navController.navigate(
                            Screen.AddEditCard.route.putArg(NavConst.COLLECTION_ID, collectionInfo.id, true)
                        )
                    },
                    onClick = {
                        navController.navigate(
                            Screen.Cards.route.putArg(NavConst.COLLECTION_ID, collectionInfo.id)
                        )
                    },
                    onMore = {
                        openSheet(CollectionSheet.Options(collectionInfo))
                    }
                )
            }
        }
    }
}

@Composable
private fun CollectionSheetHandler(
    sheet: CollectionSheet,
    viewModel: CollectionsViewModel,
    navigate: (route: String) -> Unit,
    openSheet: (newSheet: CollectionSheet?) -> Unit
) {
    val hideSheet = { openSheet(null) }

    when (sheet) {
        is CollectionSheet.Delete -> {
            AlertSheet(
                title = stringResource(R.string.delete_collection),
                message = stringResource(R.string.delete_msg),
                positive = stringResource(R.string.delete),
                onDismiss = hideSheet,
                onPositive = {
                    viewModel.deleteCollection(sheet.collection.id)
                }
            )
        }
        is CollectionSheet.Options -> {
            CollectionOptions(
                collectionName = sheet.collection.name,
                onDismiss = hideSheet,
                onClick = { option ->
                    when (option) {
                        is Option.Edit -> navigate(
                            Screen.AddEditCollection.route.putArg(NavConst.COLLECTION_ID, sheet.collection.id, true)
                        )
                        is Option.Delete -> openSheet(CollectionSheet.Delete(sheet.collection))
                        else -> { /* TODO */ }
                    }
                }
            )
        }
    }
}