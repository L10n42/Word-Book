package com.kappdev.wordbook.core.data.repository

import com.kappdev.wordbook.core.data.data_rource.CollectionDao
import com.kappdev.wordbook.core.domain.model.Collection
import com.kappdev.wordbook.core.domain.repository.CollectionRepository
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao
) : CollectionRepository {

    override suspend fun insertCollection(collection: Collection): Long {
        return collectionDao.insertCollection(collection)
    }

    override fun getCollections(): Flow<List<Collection>> {
        return collectionDao.getCollections()
    }

    override fun getCollectionById(id: Int): Collection? {
        return collectionDao.getCollectionById(id)
    }

    override fun getCollectionsInfo(): Flow<List<CollectionInfo>> {
        return collectionDao.getCollectionsInfo()
    }

    override suspend fun deleteCollection(collection: Collection): Int {
        return collectionDao.deleteCollection(collection)
    }
}