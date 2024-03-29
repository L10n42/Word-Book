package com.kappdev.wordbook.core.data.repository

import com.kappdev.wordbook.core.data.data_rource.CollectionDao
import com.kappdev.wordbook.core.domain.model.Collection
import com.kappdev.wordbook.core.domain.repository.CollectionRepository
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo
import com.kappdev.wordbook.main_feature.domain.model.CollectionPreview
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.util.Locale
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao
) : CollectionRepository {

    override suspend fun insertCollection(collection: Collection) {
        collectionDao.insertCollection(collection)
    }

    override suspend fun deleteCollectionById(id: Int) {
        collectionDao.getCollectionById(id)?.let { collection ->
            collection.backgroundImage?.let { path -> File(path).delete() }
        }
        collectionDao.deleteCollectionById(id)
    }

    override suspend fun getCollectionById(id: Int): Collection? {
        return collectionDao.getCollectionById(id)
    }

    override suspend fun getCollectionName(id: Int): String? {
        return collectionDao.getCollectionName(id)
    }

    override suspend fun getCollectionLanguage(id: Int): Locale? {
        return collectionDao.getCollectionLanguage(id)
    }

    override fun getCollectionsInfo(): Flow<List<CollectionInfo>> {
        return collectionDao.getCollectionsInfo()
    }

    override fun getCollectionsPreview(): Flow<List<CollectionPreview>> {
        return collectionDao.getCollectionsPreview()
    }

    override fun getCollectionPreview(id: Int): CollectionPreview? {
        return collectionDao.getCollectionPreview(id)
    }
}