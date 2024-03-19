package com.kappdev.wordbook.main_feature.domain.use_case

import android.content.Context
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.data.util.StoreImageException
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

    suspend operator fun invoke(collection: Collection, image: Image?): Result<Unit> {
        if (collection.name.isBlank()) {
            return Result.fail(context.getString(R.string.enter_collection_name))
        }

        val storedImage = try {
            storeImage(image)
        } catch (imageException: StoreImageException) {
            return Result.Failure(imageException)
        }

        collectionRepository.insertCollection(collection.copy(backgroundImage = storedImage))
        return Result.Success(Unit)
    }

    private fun storeImage(image: Image?): String? {
        if (image != null && image is Image.NotStored) {
            val imageResult = storageRepository.storeImage(image.uri)
            return when (imageResult) {
                is Result.Failure -> throw imageResult.exception
                is Result.Success -> imageResult.value
            }
        }
        return image?.model
    }

}