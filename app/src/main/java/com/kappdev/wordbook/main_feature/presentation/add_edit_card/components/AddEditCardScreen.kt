package com.kappdev.wordbook.main_feature.presentation.add_edit_card.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Redo
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.core.presentation.common.InputField
import com.kappdev.wordbook.core.presentation.common.VerticalSpace
import com.kappdev.wordbook.main_feature.domain.util.Image
import com.kappdev.wordbook.main_feature.presentation.add_edit_card.AddEditCardViewModel
import com.kappdev.wordbook.main_feature.presentation.common.components.AnimatedFAB
import com.kappdev.wordbook.main_feature.presentation.common.components.ImageCard
import com.kappdev.wordbook.main_feature.presentation.common.components.SimpleTopAppBar

@Composable
fun AddEditCardScreen(
    navController: NavHostController,
    viewModel: AddEditCardViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "New Card",
                elevate = scrollState.canScrollBackward,
                onBack = navController::popBackStack
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedFAB(text = "Another", icon = Icons.Rounded.Redo) {

                }
                AnimatedFAB(text = "Done", icon = Icons.Rounded.TaskAlt) {

                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            InputField(
                value = viewModel.term,
                onValueChange = viewModel::updateTerm,
                label = "Term",
                hint = "Enter term",
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            InputField(
                value = viewModel.transcription,
                onValueChange = viewModel::updateTranscription,
                label = "Transcription",
                hint = "Enter transcription - optional",
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            InputField(
                value = viewModel.definition,
                onValueChange = viewModel::updateDefinition,
                label = "Definition",
                hint = "Enter definition",
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            InputField(
                value = viewModel.example,
                onValueChange = viewModel::updateExample,
                label = "Example",
                hint = "Enter example - optional",
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            var image by remember { mutableStateOf<Image?>(null) }
            ImageCard(
                image = image,
                title = "Background image",
                onPick = { /* TODO */ },
                onDelete = { image = null },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}