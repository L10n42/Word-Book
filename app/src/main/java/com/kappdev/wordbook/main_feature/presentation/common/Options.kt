package com.kappdev.wordbook.main_feature.presentation.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Draw
import androidx.compose.material.icons.rounded.DriveFileMove
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.FactCheck
import androidx.compose.material.icons.rounded.Style
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.kappdev.wordbook.R

sealed class Option(val icon: ImageVector, @StringRes val titleRes: Int) {
    data object Edit: Option(Icons.Rounded.Edit, R.string.edit)
    data object Delete: Option(Icons.Rounded.Delete, R.string.delete)

    data object MoveTo: Option(Icons.Rounded.DriveFileMove, R.string.move_to)

    data object Flashcards: Option(Icons.Rounded.Style, R.string.flashcards)
    data object Tests: Option(Icons.Rounded.FactCheck, R.string.tests)
    data object Writing: Option(Icons.Rounded.Draw, R.string.writing)
}

@Composable
fun Option.getTitleString() = stringResource(this.titleRes)
