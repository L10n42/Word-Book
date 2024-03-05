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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kappdev.wordbook.main_feature.presentation.cards.CardsViewModel
import com.kappdev.wordbook.main_feature.presentation.common.AnimatedFAB

@Composable
fun CardsScreen(
    viewModel: CardsViewModel = viewModel()
) {

    Scaffold(
        topBar = {
            CardsTopBar(
                collectionName = "Animals",
                navigateBack = {  },
                openOptions = {  },
                openSearch = {  }
            )
        },
        floatingActionButton = {
            AnimatedFAB(text = "New", icon = Icons.Rounded.Add) {

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
                    onClick = {  }
                )
            }
        }
    }
}