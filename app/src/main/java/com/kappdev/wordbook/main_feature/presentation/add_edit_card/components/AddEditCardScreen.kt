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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.common.InputField
import com.kappdev.wordbook.core.presentation.common.LoadingDialog
import com.kappdev.wordbook.core.presentation.common.VerticalSpace
import com.kappdev.wordbook.main_feature.domain.util.CropImageInput
import com.kappdev.wordbook.main_feature.domain.util.rememberCropImageLauncher
import com.kappdev.wordbook.main_feature.domain.util.rememberPickAndCropImageLauncher
import com.kappdev.wordbook.main_feature.domain.util.rememberTakeAndCropImageLauncher
import com.kappdev.wordbook.main_feature.presentation.add_edit_card.AddEditCardViewModel
import com.kappdev.wordbook.main_feature.presentation.common.ImageSource
import com.kappdev.wordbook.main_feature.presentation.common.ImageType
import com.kappdev.wordbook.main_feature.presentation.common.components.AnimatedFAB
import com.kappdev.wordbook.main_feature.presentation.common.components.ImageCard
import com.kappdev.wordbook.main_feature.presentation.common.components.ImageUrlSheet
import com.kappdev.wordbook.main_feature.presentation.common.components.SimpleTopAppBar

@Composable
fun AddEditCardScreen(
    navController: NavHostController,
    collectionId: Int? = null,
    cardId: Int? = null,
    viewModel: AddEditCardViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val loadingDialog = viewModel.loadingDialog

    if (loadingDialog.isVisible.value) {
        LoadingDialog(loadingDialog.dialogData.value)
    }

    LaunchedEffect(Unit) {
        viewModel.getCollections()
        when {
            (cardId != null && cardId > 0) -> viewModel.getCard(cardId)
            (collectionId != null && collectionId > 0) -> viewModel.getCollection(collectionId)
            else -> navController.popBackStack()
        }
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
                    cropImage.launch(CropImageInput(uri, ImageType.Card))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = stringResource(R.string.new_card),
                elevate = scrollState.canScrollBackward,
                onBack = navController::popBackStack
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedFAB(text = stringResource(R.string.another), icon = Icons.Rounded.Redo) {
                    viewModel.saveAndAnother()
                }
                AnimatedFAB(text = stringResource(R.string.done), icon = Icons.Rounded.TaskAlt) {
                    viewModel.saveCard {
                        navController.popBackStack()
                    }
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
            CollectionChooser(
                selected = viewModel.selectedCollection,
                collections = viewModel.collections,
                onChange = viewModel::selectCollection,
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            InputField(
                value = viewModel.term,
                onValueChange = viewModel::updateTerm,
                label = stringResource(R.string.term),
                hint = stringResource(R.string.hint_enter_term),
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            InputField(
                value = viewModel.transcription,
                onValueChange = viewModel::updateTranscription,
                label = stringResource(R.string.transcription),
                hint = stringResource(R.string.hint_enter_transcription),
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            InputField(
                value = viewModel.definition,
                onValueChange = viewModel::updateDefinition,
                label = stringResource(R.string.definition),
                hint = stringResource(R.string.hint_enter_definition),
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            InputField(
                value = viewModel.example,
                onValueChange = viewModel::updateExample,
                label = stringResource(R.string.example),
                hint = stringResource(R.string.hint_enter_example),
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            ImageCard(
                image = viewModel.cardImage,
                title = stringResource(R.string.image),
                onDelete = viewModel::deleteImage,
                modifier = Modifier.fillMaxWidth(),
                onPick = { imageSource ->
                    when (imageSource) {
                        ImageSource.Camera -> takeAndCropImage.launch(ImageType.Card)
                        ImageSource.Gallery -> pickAndCropImage.launch(ImageType.Card)
                        ImageSource.Internet -> showUrlSheet = true
                    }
                }
            )
        }
    }
}