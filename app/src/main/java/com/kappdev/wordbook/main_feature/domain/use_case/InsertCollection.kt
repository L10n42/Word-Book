package com.kappdev.wordbook.main_feature.domain.use_case

import android.content.Context
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.domain.model.Collection
import com.kappdev.wordbook.core.domain.repository.CollectionRepository
import com.kappdev.wordbook.core.domain.util.Result
import com.kappdev.wordbook.core.domain.util.fail
import com.kappdev.wordbook.main_feature.domain.repository.StorageRepository
import com.kappdev.wordbook.main_feature.domain.util.Image
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InsertCollection @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val storageRepository: StorageRepository,
    @ApplicationContext private val context: Context
) {

    suspend operator fun invoke(collection: Collection, image: Image): Result<Unit> {
        if (collection.name.isBlank()) {
            return Result.fail(context.getString(R.string.enter_collection_name))
        }

        val imageResult = manageImage(image)
        when (imageResult) {
            is Result.Failure -> return Result.Failure(imageResult.exception)
            is Result.Success -> collectionRepository.insertCollection(
                collection.copy(backgroundImage = imageResult.value)
            )
        }

        return Result.Success(Unit)
    }

    private fun manageImage(image: Image): Result<String?> {
        return when (image) {
            is Image.Deleted -> {
                storageRepository.deleteImage(image.path)
                Result.Success(null)
            }
            is Image.Replaced -> {
                storageRepository.deleteImage(image.oldPath)
                storageRepository.storeImage(image.newUri)
            }
            is Image.NotStored -> storageRepository.storeImage(image.uri)
            is Image.Stored -> Result.Success(image.path)
            is Image.Empty -> Result.Success(null)
        }
    }

}