package com.kappdev.wordbook.main_feature.presentation.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.NotInterested
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.common.FieldShape
import com.kappdev.wordbook.ui.theme.CardColors

@Composable
fun ColorPicker(
    selected: Color?,
    modifier: Modifier = Modifier,
    onColorChanged: (new: Color?) -> Unit
) {
    val (isSheetVisible, showSheet) = remember { mutableStateOf(false) }

    if (isSheetVisible) {
        ColorPickerSheet(
            onDismiss = { showSheet(false) },
            onColorChanged = {
                onColorChanged(it)
                showSheet(false)
            }
        )
    }

    val animatedColor by animateColorAsState(
        targetValue = selected ?: MaterialTheme.colorScheme.background,
        label = "Card Color"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(
                color = animatedColor,
                shape = FieldShape
            )
            .clip(FieldShape)
            .clickable {
                showSheet(true)
            }
            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Palette,
            contentDescription = "Color",
            tint = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = stringResource(R.string.card_color),
            maxLines = 1,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = "Pick Color",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColorPickerSheet(
    onDismiss: () -> Unit,
    onColorChanged: (new: Color?) -> Unit
) {
    val state = rememberModalBottomSheetState(true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = state,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(50.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Icon(
                    imageVector = Icons.Rounded.NotInterested,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Clear color",
                    modifier = Modifier.colorItem(
                        color = MaterialTheme.colorScheme.surface,
                        onClick = { onColorChanged(null) }
                    )
                )
            }
            items(CardColors) { color ->
                Box(
                    Modifier.colorItem(
                        color = color,
                        borderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(0.5f)),
                        onClick = { onColorChanged(color) }
                    )
                )
            }
        }
    }
}

private fun Modifier.colorItem(
    color: Color,
    borderStroke: BorderStroke? = null,
    onClick: () -> Unit
) = this
    .aspectRatio(1f)
    .shadow(4.dp, CircleShape)
    .background(color, CircleShape)
    .then(borderStroke?.let { Modifier.border(it, CircleShape) } ?: Modifier)
    .clip(CircleShape)
    .clickable(onClick = onClick)