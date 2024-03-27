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
import com.kappdev.wordbook.core.presentation.common.AlertSheet
import com.kappdev.wordbook.core.presentation.common.FABPadding
import com.kappdev.wordbook.core.presentation.navigation.NavConst
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.core.presentation.navigation.putArg
import com.kappdev.wordbook.main_feature.presentation.cards.CardSheet
import com.kappdev.wordbook.main_feature.presentation.cards.CardsViewModel
import com.kappdev.wordbook.main_feature.presentation.common.Option
import com.kappdev.wordbook.main_feature.presentation.common.components.AnimatedFAB
import com.kappdev.wordbook.main_feature.presentation.common.components.CollectionsSheet
import com.kappdev.wordbook.main_feature.presentation.common.components.EmptyScreen

@Composable
fun CardsScreen(
    navController: NavHostController,
    collectionId: Int?,
    viewModel: CardsViewModel = hiltViewModel()
) {
    val (cardSheet, openSheet) = remember { mutableStateOf<CardSheet?>(null) }

    if (cardSheet != null) {
        CardSheetHandler(cardSheet, viewModel, navController::navigate, openSheet)
    }

    LaunchedEffect(Unit) {
        when {
            (collectionId != null && collectionId > 0) -> {
                viewModel.getCards(collectionId)
                viewModel.getCollectionInfo(collectionId)
            }
            else -> navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            var searchValue by remember { mutableStateOf("") }
            CardsTopBar(
                collectionName = viewModel.collectionName ?: "",
                searchValue = searchValue,
                onSearch = { searchValue = it },
                navigateBack = navController::popBackStack,
                openOptions = { /*TODO*/ }
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
            contentPadding = PaddingValues(bottom = FABPadding, top = 16.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.cards, { it.id }) { card ->
                TermCard(
                    card = card,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        openSheet(CardSheet.Options(card))
                    },
                    onSpeak = viewModel::speak
                )
            }
        }
        if (viewModel.cards.isEmpty()) {
            EmptyScreen(
                message = stringResource(R.string.no_cards_msg),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun CardSheetHandler(
    sheet: CardSheet,
    viewModel: CardsViewModel,
    navigate: (route: String) -> Unit,
    openSheet: (newSheet: CardSheet?) -> Unit
) {
    val hideSheet = { openSheet(null) }

    when (sheet) {
        is CardSheet.Delete -> {
            AlertSheet(
                title = stringResource(R.string.delete_card),
                message = stringResource(R.string.card_delete_msg),
                positive = stringResource(R.string.delete),
                onDismiss = hideSheet,
                onPositive = {
                    viewModel.deleteCard(sheet.card.id)
                }
            )
        }

        is CardSheet.Options -> {
            CardOptions(
                cardTerm = sheet.card.term,
                onDismiss = hideSheet,
                onClick = { option ->
                    when (option) {
                        is Option.Edit -> navigate(
                            Screen.AddEditCard.route.putArg(NavConst.CARD_ID, sheet.card.id, true)
                        )
                        is Option.MoveTo -> openSheet(CardSheet.PickCollection(sheet.card.id))
                        is Option.Delete -> openSheet(CardSheet.Delete(sheet.card))
                        else -> { /* TODO */ }
                    }
                }
            )
        }

        is CardSheet.PickCollection -> {
            CollectionsSheet(
                selected = null,
                collections = viewModel.collections,
                onDismiss = hideSheet,
                onSelect = { selectedCollection ->
                    viewModel.moveTo(sheet.cardId, selectedCollection.id)
                }
            )
        }
    }
}