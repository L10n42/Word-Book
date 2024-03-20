package com.kappdev.wordbook.core.domain.repository

import com.kappdev.wordbook.core.domain.model.Collection
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo
import com.kappdev.wordbook.main_feature.domain.model.CollectionPreview
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {

    suspend fun insertCollection(collection: Collection)

    suspend fun getCollectionById(id: Int): Collection?

    suspend fun getCollectionName(id: Int): String?

    fun getCollectionsInfo(): Flow<List<CollectionInfo>>

    fun getCollectionsPreview(): Flow<List<CollectionPreview>>

    fun getCollectionPreview(id: Int): CollectionPreview?

    suspend fun deleteCollectionById(id: Int)

}