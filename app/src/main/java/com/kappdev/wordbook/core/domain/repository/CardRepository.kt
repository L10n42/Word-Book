package com.kappdev.wordbook.core.domain.repository

interface CardRepository {

    fun getCardsCount(collectionId: Int): Int
}