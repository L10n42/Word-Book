package com.kappdev.wordbook.main_feature.presentation.add_edit_collection.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kappdev.wordbook.core.presentation.common.FieldShape

@Composable
fun ChooseLanguageButton(
    label: String,
    selected: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = FieldShape
            )
            .clip(FieldShape)
            .clickable(onClick = onClick)
    ) {
        val (title, content, icon) = createRefs()

        Text(
            text = selected,
            maxLines = 1,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.constrainAs(content) {
                start.linkTo(parent.start, 8.dp)
                bottom.linkTo(parent.bottom, 8.dp)
                top.linkTo(title.bottom, 2.dp)
                end.linkTo(icon.start, 4.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = label,
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start, 8.dp)
                top.linkTo(parent.top, 4.dp)
            }
        )

        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = "Go button",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.constrainAs(icon) {
                end.linkTo(parent.end, 4.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}