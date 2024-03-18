package com.kappdev.wordbook.main_feature.presentation.add_edit_collection.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.domain.util.TextToSpeechHelper
import com.kappdev.wordbook.core.domain.util.rememberPickImageLauncher
import com.kappdev.wordbook.core.domain.util.rememberTakePictureLauncher
import com.kappdev.wordbook.core.presentation.common.InputField
import com.kappdev.wordbook.core.presentation.common.VerticalSpace
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.main_feature.presentation.add_edit_collection.AddEditCollectionViewModel
import com.kappdev.wordbook.main_feature.presentation.common.ImageSource
import com.kappdev.wordbook.main_feature.presentation.common.components.AnimatedFAB
import com.kappdev.wordbook.main_feature.presentation.common.components.ColorPicker
import com.kappdev.wordbook.main_feature.presentation.common.components.ImageCard
import com.kappdev.wordbook.main_feature.presentation.common.components.ImageSourceChooser
import com.kappdev.wordbook.main_feature.presentation.common.components.SimpleTopAppBar

@Composable
fun AddEditCollectionScreen(
    collectionId: Int?,
    navController: NavHostController,
    viewModel: AddEditCollectionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val ttsHelper = remember { TextToSpeechHelper(context) }

    LaunchedEffect(Unit) {
        if (collectionId != null && collectionId > 0) {
            viewModel.getCollection(collectionId)
        }
    }

    val pickImageLauncher = rememberPickImageLauncher { it?.let(viewModel::updateCover) }
    val takePictureLauncher = rememberTakePictureLauncher { it?.let(viewModel::updateCover) }

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = stringResource(R.string.new_collection),
                elevate = scrollState.canScrollBackward,
                onBack = navController::popBackStack
            )
        },
        floatingActionButton = {
            AnimatedFAB(text = stringResource(R.string.done), icon = Icons.Rounded.TaskAlt) {
                viewModel.saveCollection {
                    navController.navigate(Screen.Collections.route)
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
                value = viewModel.name,
                onValueChange = viewModel::updateName,
                label = stringResource(R.string.name),
                hint = stringResource(R.string.enter_collection_name),
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            InputField(
                value = viewModel.description,
                onValueChange = viewModel::updateDescription,
                label = stringResource(R.string.description),
                hint = stringResource(R.string.enter_description),
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(32.dp)

            LanguageChooser(
                label = stringResource(R.string.term_language),
                selected = viewModel.termLanguage,
                availableLocales = ttsHelper.availableLanguages,
                onChange = viewModel::updateTermLanguage,
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            LanguageChooser(
                label = stringResource(R.string.definition_language),
                selected = viewModel.definitionLanguage,
                availableLocales = ttsHelper.availableLanguages,
                onChange = viewModel::updateDefinitionLanguage,
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(32.dp)

            ColorPicker(
                selected = viewModel.color,
                onColorChanged = viewModel::updateColor,
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            var showChooser by remember { mutableStateOf(false) }
            ImageCard(
                image = viewModel.cover,
                title = stringResource(R.string.background_image),
                onPick = { showChooser = true },
                onDelete = viewModel::removeCover,
                modifier = Modifier.fillMaxWidth()
            )

            if (showChooser) {
                ImageSourceChooser(
                    onDismiss = { showChooser = false },
                    onClick = { imageSource ->
                        when (imageSource) {
                            ImageSource.Camera -> takePictureLauncher.launch(Unit)
                            ImageSource.Gallery -> pickImageLauncher.launch(Unit)
                            ImageSource.Internet -> { /* TODO */ }
                        }
                    }
                )
            }
        }
    }
}