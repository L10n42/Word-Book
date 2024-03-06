package com.kappdev.wordbook.core.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun InputField(
    value: String,
    modifier: Modifier = Modifier,
    hint: String? = null,
    label: String? = null,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val (focusState, changeFocus) = remember { mutableStateOf<FocusState?>(null) }

    val animatedElevation by animateDpAsState(
        targetValue = if (focusState?.isFocused == true) 6.dp else 0.dp,
        label = "Animated Elevation"
    )

    KeyboardHiddenEffect {
        focusManager.clearFocus()
    }

    BasicTextField(
        value = value,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged(changeFocus)
            .shadow(
                elevation = animatedElevation,
                shape = FieldShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = FieldShape
            ),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp, lineHeight = 18.sp
        ),
        decorationBox = { innerTextField ->
            ConstraintLayout {
                val (textLabel, content, clear) = createRefs()

                ContentWithPlaceholder(
                    placeholder = hint,
                    showPlaceholder = value.isEmpty(),
                    content = innerTextField,
                    modifier = Modifier.constrainAs(content) {
                        start.linkTo(parent.start, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                        if (label != null) top.linkTo(textLabel.bottom, 2.dp) else top.linkTo(parent.top, 8.dp)
                        if (value.isNotEmpty()) end.linkTo(clear.start, 4.dp) else end.linkTo(parent.end, 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )

                if (label != null) {
                    Label(
                        label = label,
                        modifier = Modifier.constrainAs(textLabel) {
                            start.linkTo(parent.start, 8.dp)
                            top.linkTo(parent.top, 4.dp)
                        }
                    )
                }

                ClearButton(
                    isVisible = value.isNotEmpty(),
                    onClear = { onValueChange("") },
                    modifier = Modifier.constrainAs(clear) {
                        end.linkTo(parent.end, 4.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )

            }
        }
    )
}

@Composable
private fun ContentWithPlaceholder(
    placeholder: String?,
    showPlaceholder: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier) {
        content()
        if (placeholder != null && showPlaceholder) {
            Text(
                text = placeholder,
                style = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}

@Composable
private fun Label(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        maxLines = 1,
        fontSize = 12.sp,
        modifier = modifier,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.primary,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun ClearButton(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    onClear: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier,
        enter = fadeIn() + scaleIn(),
        exit = scaleOut() + fadeOut()
    ) {
        IconButton(
            onClick = onClear,
            enabled = isVisible,
            modifier = Modifier.size(26.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Cancel,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Clear",
                modifier = Modifier
                    .size(18.dp)
                    .alpha(0.64f)
            )
        }
    }
}