package com.kappdev.wordbook.core.data.repository

import com.kappdev.wordbook.core.data.data_rource.CardDao
import com.kappdev.wordbook.core.domain.repository.CardRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao
) : CardRepository {

    override fun getCardsCount(collectionId: Int): Int {
        return cardDao.getCardsCount(collectionId)
    }
}