package com.kappdev.wordbook.core.domain.repository

import com.kappdev.wordbook.core.domain.model.Collection
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {

    suspend fun insertCollection(collection: Collection)

    suspend fun getCollectionById(id: Int): Collection?

    fun getCollectionsInfo(): Flow<List<CollectionInfo>>

    suspend fun deleteCollectionById(id: Int)

}