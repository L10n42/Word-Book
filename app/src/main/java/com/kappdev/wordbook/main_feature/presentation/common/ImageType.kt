package com.kappdev.wordbook.main_feature.presentation.common

sealed class ImageType(val aspectRatioX: Int, val aspectRatioY: Int) {
    data object Cover: ImageType(16, 9)
    data object Card: ImageType(1, 1)
}