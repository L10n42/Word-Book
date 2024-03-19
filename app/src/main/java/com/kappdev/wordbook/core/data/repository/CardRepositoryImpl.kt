package com.kappdev.wordbook.core.data.repository

import com.kappdev.wordbook.core.data.data_rource.CardDao
import com.kappdev.wordbook.core.domain.model.Card
import com.kappdev.wordbook.core.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao
) : CardRepository {

    override suspend fun insertCard(card: Card) {
        cardDao.insertCard(card)
    }

    override suspend fun getCardById(id: Int): Card? {
        return cardDao.getCardById(id)
    }

    override fun getCollectionCards(collectionId: Int): Flow<List<Card>> {
        return cardDao.getCollectionCards(collectionId)
    }

    override suspend fun deleteCollectionCards(collectionId: Int) {
        cardDao.getCollectionCards(collectionId).first().forEach { card ->
            card.image?.let { path -> File(path).delete() }
        }
        cardDao.deleteCollectionCards(collectionId)
    }

}