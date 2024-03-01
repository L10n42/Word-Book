package com.kappdev.wordbook.main_feature.domain.model

import androidx.compose.ui.graphics.Color

data class CollectionInfo(
    val id: Int,
    val name: String,
    val description: String,
    val cardsCount: Int,
    val color: Color? = null,
    val backgroundImage: String? = null
)
