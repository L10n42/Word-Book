package com.kappdev.wordbook.main_feature.presentation.cards.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.navigation.NavConst
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.core.presentation.navigation.putArg
import com.kappdev.wordbook.main_feature.presentation.cards.CardsViewModel
import com.kappdev.wordbook.main_feature.presentation.common.components.AnimatedFAB

@Composable
fun CardsScreen(
    navController: NavHostController,
    collectionId: Int?,
    viewModel: CardsViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        when {
            (collectionId != null && collectionId > 0) -> {
                viewModel.getCards(collectionId)
                viewModel.getTitle(collectionId)
            }
            else -> navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            CardsTopBar(
                collectionName = viewModel.collectionName ?: "",
                navigateBack = navController::popBackStack,
                openOptions = {  },
                openSearch = {  }
            )
        },
        floatingActionButton = {
            AnimatedFAB(text = stringResource(R.string.btn_new), icon = Icons.Rounded.Add) {
                navController.navigate(
                    Screen.AddEditCard.route.putArg(NavConst.COLLECTION_ID, collectionId, true)
                )
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
            items(viewModel.cards, { it.id }) { card ->
                TermCard(
                    card = card,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navController.navigate(
                            Screen.AddEditCard.route.putArg(NavConst.CARD_ID, card.id, true)
                        )
                    }
                )
            }
        }
    }
}