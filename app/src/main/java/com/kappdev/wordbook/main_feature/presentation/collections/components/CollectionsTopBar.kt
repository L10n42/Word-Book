package com.kappdev.wordbook.main_feature.presentation.collections.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kappdev.wordbook.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CollectionsTopBar(

) {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val (title, settings, searchBar) = createRefs()

        CollectionsTitle(
            Modifier.constrainAs(title) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top, 16.dp)
            }
        )

        SettingsIcon(
            Modifier.constrainAs(settings) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            },
            onClick = {}
        )

        var searchJob = remember<Job?> { null }
        var searchArg by remember { mutableStateOf("") }
        var isSearching by remember { mutableStateOf(false) }

        suspend fun search() {
            isSearching = true
            delay(2000)
            isSearching = false
        }

        LaunchedEffect(searchArg) {
            delay(300)
            searchJob?.cancel()
            if (searchArg.isNotEmpty()) {
                searchJob = launch { search() }
            }
        }

        SearchBox(
            value = searchArg,
            isSearching = isSearching,
            openOptions = {  },
            onValueChange = { searchArg = it },
            modifier = Modifier.constrainAs(searchBar) {
                end.linkTo(parent.end, 16.dp)
                start.linkTo(parent.start, 16.dp)
                top.linkTo(title.bottom, 24.dp)
                bottom.linkTo(parent.bottom, 16.dp)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
private fun CollectionsTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.collections),
        maxLines = 1,
        fontSize = 20.sp,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.SemiBold,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun SettingsIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(0f) }

    IconButton(
        modifier = modifier.rotate(rotation.value),
        onClick = {
            scope.launch {
                rotation.animateTo(rotation.value + 360f)
            }
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Settings,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = "Settings"
        )
    }
}