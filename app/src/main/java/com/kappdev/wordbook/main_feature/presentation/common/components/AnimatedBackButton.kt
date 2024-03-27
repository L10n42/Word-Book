package com.kappdev.wordbook.main_feature.presentation.common.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun AnimatedBackButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    var targetRotation by remember { mutableStateOf(0f) }

    val rotation by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = tween(300, easing = FastOutLinearInEasing),
        label = "Back Button Rotation"
    )

    androidx.compose.material.IconButton(
        modifier = modifier.rotate(rotation),
        enabled = enabled,
        onClick = {
            targetRotation += 360f
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null
        )
    }
}