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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.navigation.NavConst
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.core.presentation.navigation.putArg
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo
import com.kappdev.wordbook.main_feature.presentation.collections.CollectionsViewModel
import com.kappdev.wordbook.main_feature.presentation.common.Option
import com.kappdev.wordbook.main_feature.presentation.common.components.AnimatedFAB

@Composable
fun CollectionsScreen(
    navController: NavHostController,
    viewModel: CollectionsViewModel = hiltViewModel()
) {
    val (optionsCollection, openOptions) = remember { mutableStateOf<CollectionInfo?>(null) }

    if (optionsCollection != null) {
        CollectionOptions(
            collectionName = optionsCollection.name,
            onDismiss = { openOptions(null) },
            onClick = { option ->
                when (option) {
                    is Option.Edit -> navController.navigate(
                        Screen.AddEditCollection.route.putArg(NavConst.COLLECTION_ID, optionsCollection.id, true)
                    )
                    else -> { /* TODO */ }
                }
            }
        )
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
                    cardMoreOpened = (optionsCollection == collectionInfo),
                    modifier = Modifier.fillMaxWidth(),
                    onNewCard = {},
                    onClick = {
                        navController.navigate(Screen.Cards.route)
                    },
                    onMore = {
                        openOptions(collectionInfo)
                    }
                )
            }
        }
    }
}