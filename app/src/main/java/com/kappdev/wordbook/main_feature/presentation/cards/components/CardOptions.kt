package com.kappdev.wordbook.main_feature.presentation.cards.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.core.presentation.common.CustomModalBottomSheet
import com.kappdev.wordbook.main_feature.presentation.common.Option
import com.kappdev.wordbook.main_feature.presentation.common.components.DragHandleTitle
import com.kappdev.wordbook.main_feature.presentation.common.components.OptionsLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardOptions(
    cardTerm: String,
    onDismiss: () -> Unit,
    onClick: (option: Option) -> Unit
) {
    CustomModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = {
            DragHandleTitle(title = cardTerm)
        }
    ) { triggerDismiss ->
        OptionsLayout(
            Option.Edit, Option.MoveTo, Option.Delete,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp, top = 8.dp)
        ) { clickedOption ->
            onClick(clickedOption)
            triggerDismiss()
        }
    }
}