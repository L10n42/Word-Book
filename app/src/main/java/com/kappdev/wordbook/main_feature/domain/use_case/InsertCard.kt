package com.kappdev.wordbook.main_feature.domain.use_case

import android.content.Context
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.data.util.StoreImageException
import com.kappdev.wordbook.core.domain.model.Card
import com.kappdev.wordbook.core.domain.repository.CardRepository
import com.kappdev.wordbook.core.domain.util.Result
import com.kappdev.wordbook.core.domain.util.fail
import com.kappdev.wordbook.main_feature.domain.repository.StorageRepository
import com.kappdev.wordbook.main_feature.domain.util.Image
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InsertCard @Inject constructor(
    private val cardRepository: CardRepository,
    private val storageRepository: StorageRepository,
    @ApplicationContext private val context: Context
) {

    suspend operator fun invoke(card: Card, image: Image?): Result<Unit> {
        validateValues(card)?.let { return it }

        val storedImage = try {
            storeImage(image)
        } catch (imageException: StoreImageException) {
            return Result.Failure(imageException)
        }

        cardRepository.insertCard(card.copy(image = storedImage))
        return Result.Success(Unit)
    }

    private fun validateValues(card: Card): Result.Failure? {
        return when {
            card.term.isBlank() -> {
                Result.fail(context.getString(R.string.error_blank_term))
            }
            card.definition.isBlank() -> {
                Result.fail(context.getString(R.string.error_blank_definition))
            }
            else -> null
        }
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