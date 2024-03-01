package com.kappdev.wordbook.main_feature.presentation.collections.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import com.kappdev.wordbook.R
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo

@Composable
fun CollectionCard(
    info: CollectionInfo,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    onMore: () -> Unit,
    onNewCard: () -> Unit,
    onClick: () -> Unit
) {
    val backgroundColor = info.color ?: MaterialTheme.colorScheme.surface

    ConstraintLayout(
        modifier
            .shadow(elevation = 8.dp, shape = shape)
            .background(backgroundColor, shape)
            .clip(shape)
            .clickable(onClick = onClick)
    ) {
        val (name, description, button, cardsCount, more, background) = createRefs()

        if (info.backgroundImage != null) {
            BackgroundImage(
                image = info.backgroundImage,
                modifier = Modifier.constrainAs(background) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.matchParent
                }
            )
        }

        CollectionName(
            name = info.name,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top, 16.dp)
                end.linkTo(more.start)
                width = Dimension.fillToConstraints
            }
        )

        CollectionDescription(
            description = info.description,
            modifier = Modifier.constrainAs(description) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(name.bottom)
                end.linkTo(more.start)
                width = Dimension.fillToConstraints
            }
        )

        ActionButton(
            modifier = Modifier.constrainAs(button) {
                start.linkTo(parent.start, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
                top.linkTo(description.bottom, 16.dp)
            },
            onClick = onNewCard
        )

        CardsCount(
            count = info.cardsCount,
            modifier = Modifier.constrainAs(cardsCount) {
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }
        )

        CardMore(
            modifier = Modifier.constrainAs(more) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            },
            onClick = onMore
        )
    }
}

@Composable
private fun CollectionName(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        maxLines = 1,
        fontSize = 18.sp,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.SemiBold,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun CollectionDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = description,
        maxLines = 2,
        fontSize = 16.sp,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Medium,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = stringResource(R.string.btn_new_card),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun CardsCount(
    count: Int,
    modifier: Modifier = Modifier
) {
    val textToDisplay = when (count) {
        1 -> stringResource(R.string.card_count)
        else -> stringResource(R.string.cards_count, count)
    }

    Text(
        text = textToDisplay,
        maxLines = 1,
        fontSize = 16.sp,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Medium,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun CardMore(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Rounded.MoreHoriz,
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = "Card More"
        )
    }
}

@Composable
private fun BackgroundImage(
    image: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = image,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        contentDescription = "Collection background image"
    )
}