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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kappdev.wordbook.core.presentation.common.InputField
import com.kappdev.wordbook.core.presentation.common.VerticalSpace
import com.kappdev.wordbook.main_feature.presentation.common.AnimatedFAB
import com.kappdev.wordbook.main_feature.presentation.common.ColorPicker
import com.kappdev.wordbook.main_feature.presentation.common.ImageCard
import com.kappdev.wordbook.main_feature.presentation.common.SimpleTopAppBar

@Composable
fun AddEditCollectionScreen(
    navController: NavHostController
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "New Collection",
                elevate = scrollState.canScrollBackward,
                onBack = navController::popBackStack
            )
        },
        floatingActionButton = {
            AnimatedFAB(text = "Done", icon = Icons.Rounded.TaskAlt) {

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
            val (name, changeName) = remember { mutableStateOf("") }
            val (description, changeDescription) = remember { mutableStateOf("") }

            InputField(
                value = name,
                onValueChange = changeName,
                label = "Name",
                hint = "Enter collection name",
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            InputField(
                value = description,
                onValueChange = changeDescription,
                label = "Description",
                hint = "Enter description - optional",
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(32.dp)
            
            ChooseLanguageButton(
                label = "Term language",
                selected = "English",
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            ChooseLanguageButton(
                label = "Definition language",
                selected = "English",
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(32.dp)

            var color by remember { mutableStateOf<Color?>(null) }
            ColorPicker(
                selected = color,
                onColorChanged = { color = it },
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpace(16.dp)

            var image by remember { mutableStateOf<String?>(null) }
            ImageCard(
                image = image,
                title = "Background image",
                onPick = { image = "https://www.princeton.edu/sites/default/files/styles/1x_full_2x_half_crop/public/images/2022/02/KOA_Nassau_2697x1517.jpg?itok=Bg2K7j7J" },
                onDelete = { image = null },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}