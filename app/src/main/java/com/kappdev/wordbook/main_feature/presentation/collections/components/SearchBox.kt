package com.kappdev.wordbook.main_feature.presentation.collections.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.common.KeyboardHiddenEffect

@Composable
fun SearchBox(
    value: String,
    isSearching: Boolean,
    modifier: Modifier = Modifier,
    openOptions: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val (focusState, changeFocus) = remember { mutableStateOf<FocusState?>(null) }

    KeyboardHiddenEffect {
        focusManager.clearFocus()
    }

    BasicTextField(
        value = value,
        singleLine = true,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged(changeFocus),
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
        decorationBox = { innerTextField ->
            SearchContainer {
                Crossfade(
                    targetState = isSearching,
                    modifier = Modifier.padding(8.dp),
                    label = "Searching"
                ) { searching ->
                    when {
                        searching -> SearchProgressIndicator()
                        else -> SearchIcon(focusState?.isFocused ?: false)
                    }

                }

                ContentWithPlaceholder(
                    content = innerTextField,
                    showPlaceholder = value.isEmpty(),
                    modifier = Modifier.weight(1f)
                )

                OptionsButton(onClick = openOptions)
            }
        }
    )
}

@Composable
private fun SearchContainer(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        content = content,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.background(MaterialTheme.colorScheme.background, CircleShape)
    )
}

@Composable
private fun SearchProgressIndicator() {
    CircularProgressIndicator(
        strokeWidth = 2.dp,
        strokeCap = StrokeCap.Round,
        modifier = Modifier.size(24.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun SearchIcon(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    val transition = updateTransition(targetState = isActive, label = "Search Icon Transition")

    val rotationZ by transition.animateFloat(
        transitionSpec = { tween(600, easing = LinearEasing) },
        label = "Icon Z rotation",
        targetValueByState = { activeState -> if (activeState) 90f else 0f }
    )

    val rotationY by transition.animateFloat(
        transitionSpec = { tween(400, 150, LinearEasing) },
        label = "Icon Y rotation",
        targetValueByState = { activeState -> if (activeState) 360f else 0f }
    )

    Icon(
        imageVector = Icons.Rounded.Search,
        contentDescription = "Search",
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.graphicsLayer {
            this.rotationZ = rotationZ
            this.rotationY = rotationY
            this.cameraDistance = 5f * density
        }
    )
}

@Composable
private fun ContentWithPlaceholder(
    showPlaceholder: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier) {
        content()
        if (showPlaceholder) {
            Text(
                text = stringResource(R.string.search),
                style = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}

@Composable
private fun OptionsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Rounded.Tune,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = "Options"
        )
    }
}