package com.kappdev.wordbook.main_feature.presentation.collections.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.common.CardElevation
import com.kappdev.wordbook.core.presentation.common.CardShape
import com.kappdev.wordbook.core.presentation.common.PressedCardElevation
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo

@Composable
fun CollectionCard(
    info: CollectionInfo,
    cardMoreOpened: Boolean,
    modifier: Modifier = Modifier,
    onMore: () -> Unit,
    onNewCard: () -> Unit,
    onClick: () -> Unit
) {
    val backgroundColor = info.color ?: MaterialTheme.colorScheme.surface
    val interactionSource = remember { MutableInteractionSource() }
    val isCardPressed by interactionSource.collectIsPressedAsState()

    val cardElevation by animateDpAsState(
        targetValue = if (isCardPressed) PressedCardElevation else CardElevation,
        label = "Card Elevation"
    )

    ConstraintLayout(
        modifier
            .shadow(elevation = cardElevation, shape = CardShape)
            .background(backgroundColor, CardShape)
            .clip(CardShape)
            .clickable(interactionSource, indication = LocalIndication.current, onClick = onClick)
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
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
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
            cardMoreOpened = cardMoreOpened,
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
        0 -> stringResource(R.string.has_no_cards)
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

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
private fun CardMore(
    cardMoreOpened: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val avdIcon = AnimatedImageVector.animatedVectorResource(R.drawable.avd_more_horiz)
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = rememberAnimatedVectorPainter(animatedImageVector = avdIcon, atEnd = cardMoreOpened),
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
    val painter = rememberAsyncImagePainter(image)

    val transition by animateFloatAsState(
        targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
        animationSpec = tween(400),
        label = "Image Display Transition"
    )

    Box(
        modifier.alpha(transition)
    ) {
        Image(
            painter = painter,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            contentDescription = "Collection background image"
        )
        Box(
            Modifier
                .matchParentSize()
                .background(Color.White.copy(alpha = 0.42f))
        )
    }
}