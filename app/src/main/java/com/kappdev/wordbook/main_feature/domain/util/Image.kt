package com.kappdev.wordbook.main_feature.domain.util

import android.net.Uri

sealed class Image(val model: String) {
    data class Stored(val path: String): Image(path)
    data class NotStored(val uri: Uri): Image(uri.toString())
    data class FromInternet(val url: String): Image(url)
}