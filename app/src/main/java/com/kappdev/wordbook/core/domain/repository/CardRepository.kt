package com.kappdev.wordbook.core.domain.repository

import com.kappdev.wordbook.core.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    suspend fun insertCard(card: Card)

    suspend fun getCardById(id: Int): Card?

    fun getCollectionCards(collectionId: Int): Flow<List<Card>>

    suspend fun deleteCollectionCards(collectionId: Int)
}