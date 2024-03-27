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
import com.kappdev.wordbook.core.presentation.common.FABPadding
import com.kappdev.wordbook.core.presentation.common.InputField
import com.kappdev.wordbook.core.presentation.common.LoadingDialog
import com.kappdev.wordbook.core.presentation.common.VerticalSpace
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.main_feature.domain.util.CropImageInput
import com.kappdev.wordbook.main_feature.domain.util.rememberCropImageLauncher
import com.kappdev.wordbook.main_feature.domain.util.rememberPickAndCropImageLauncher
import com.kappdev.wordbook.main_feature.domain.util.rememberTakeAndCropImageLauncher
import com.kappdev.wordbook.main_feature.presentation.add_edit_collection.AddEditCollectionViewModel
import com.kappdev.wordbook.main_feature.presentation.common.ImageSource
import com.kappdev.wordbook.main_feature.presentation.common.ImageType
import com.kappdev.wordbook.main_feature.presentation.common.components.AnimatedFAB
import com.kappdev.wordbook.main_feature.presentation.common.components.ColorPicker
import com.kappdev.wordbook.main_feature.presentation.common.components.ImageCard
import com.kappdev.wordbook.main_feature.presentation.common.components.ImageUrlSheet
import com.kappdev.wordbook.main_feature.presentation.common.components.SimpleTopAppBar

@Composable
fun AddEditCollectionScreen(
    collectionId: Int?,
    navController: NavHostController,
    viewModel: AddEditCollectionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val loadingDialog = viewModel.loadingDialog
    val ttsHelper = remember { TextToSpeechHelper(context) }

    LaunchedEffect(Unit) {
        if (collectionId != null && collectionId > 0) {
            viewModel.getCollection(collectionId)
        }
    }

    if (loadingDialog.isVisible.value) {
        LoadingDialog(loadingDialog.dialogData.value)
    }

    val takeAndCropImage = rememberTakeAndCropImageLauncher(onResult = viewModel::handleCropImageResult)
    val pickAndCropImage = rememberPickAndCropImageLauncher(onResult = viewModel::handleCropImageResult)
    val cropImage = rememberCropImageLauncher(onResult = viewModel::handleCropImageResult)

    var showUrlSheet by remember { mutableStateOf(false) }
    if (showUrlSheet) {
        ImageUrlSheet(
            onDismiss = { showUrlSheet = false },
            onDownload = { imageUrl ->
                viewModel.downloadImageFromUrl(imageUrl) { uri ->
                    cropImage.launch(CropImageInput(uri, ImageType.Cover))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = when {
                    (collectionId != null && collectionId > 0) -> stringResource(R.string.edit_collection)
                    else -> stringResource(R.string.new_collection)
                },
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
                .padding(bottom = FABPadding, top = 16.dp, start = 16.dp, end = 16.dp)
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
                availableLocales = ttsHelper.availableLanguages.value,
                onChange = viewModel::updateTermLanguage,
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            LanguageChooser(
                label = stringResource(R.string.definition_language),
                selected = viewModel.definitionLanguage,
                availableLocales = ttsHelper.availableLanguages.value,
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

            ImageCard(
                image = viewModel.cover,
                title = stringResource(R.string.background_image),
                onDelete = viewModel::deleteCover,
                modifier = Modifier.fillMaxWidth(),
                onPick = { imageSource ->
                    when (imageSource) {
                        ImageSource.Camera -> takeAndCropImage.launch(ImageType.Cover)
                        ImageSource.Gallery -> pickAndCropImage.launch(ImageType.Cover)
                        ImageSource.Internet -> showUrlSheet = true
                    }
                }
            )
        }
    }
}