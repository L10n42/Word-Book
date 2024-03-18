package com.kappdev.wordbook.main_feature.domain.repository

import android.net.Uri
import com.kappdev.wordbook.core.domain.util.Result

interface StorageRepository {

    fun storeImage(uri: Uri): Result<String>

}