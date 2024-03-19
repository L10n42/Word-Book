package com.kappdev.wordbook.core.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kappdev.wordbook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertSheet(
    title: String,
    message: String,
    positive: String = stringResource(R.string.ok),
    negative: String = stringResource(R.string.cancel),
    onDismiss: () -> Unit,
    onNegative: () -> Unit = {},
    onPositive: () -> Unit
) {
    CustomModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null
    ) { triggerDismiss ->
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 32.dp, bottom = 16.dp)
        ) {
            AlertTitle(title)
            VerticalSpace(8.dp)
            AlertMessage(message)

            VerticalSpace(24.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                NegativeButton(negative) {
                    onNegative()
                    triggerDismiss()
                }
                HorizontalSpace(16.dp)
                PositiveButton(positive) {
                    onPositive()
                    triggerDismiss()
                }
            }
        }
    }
}

@Composable
private fun NegativeButton(
    negative: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Text(
            text = negative,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun PositiveButton(
    positive: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Text(
            text = positive,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun AlertMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = message,
        maxLines = 5,
        fontSize = 16.sp,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Medium,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun AlertTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        maxLines = 2,
        fontSize = 20.sp,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.SemiBold,
        overflow = TextOverflow.Ellipsis
    )
}