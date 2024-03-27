package com.kappdev.wordbook.core.data.data_rource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kappdev.wordbook.core.domain.model.Collection
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo
import com.kappdev.wordbook.main_feature.domain.model.CollectionPreview
import kotlinx.coroutines.flow.Flow
import java.util.Locale

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: Collection)

    @Query("SELECT * FROM collections WHERE collection_id = :id LIMIT 1")
    suspend fun getCollectionById(id: Int): Collection?

    @Query("DELETE FROM collections WHERE collection_id = :id")
    suspend fun deleteCollectionById(id: Int)

    @Query("SELECT name FROM collections WHERE collection_id = :id LIMIT 1")
    suspend fun getCollectionName(id: Int): String?

    @Query("SELECT term_language FROM collections WHERE collection_id = :id LIMIT 1")
    suspend fun getCollectionLanguage(id: Int): Locale?

    @Query("""
        SELECT 
        c.collection_id AS id,
        c.name,
        c.description,
        COUNT(card.card_id) AS cardsCount,
        c.card_color AS color,
        c.background_image AS backgroundImage
        FROM collections c 
        LEFT JOIN cards card ON c.collection_id = card.collection_id 
        GROUP BY c.collection_id
    """)
    fun getCollectionsInfo(): Flow<List<CollectionInfo>>

    @Query("""
        SELECT 
        c.collection_id AS id,
        c.name,
        COUNT(card.card_id) AS cardsCount
        FROM collections c
        LEFT JOIN cards card ON c.collection_id = card.collection_id 
        GROUP BY c.collection_id
    """)
    fun getCollectionsPreview(): Flow<List<CollectionPreview>>

    @Query("""
        SELECT 
        c.collection_id AS id,
        c.name,
        COUNT(card.card_id) AS cardsCount
        FROM collections c
        LEFT JOIN cards card ON c.collection_id = card.collection_id
        WHERE c.collection_id = :id
        GROUP BY c.collection_id
    """)
    fun getCollectionPreview(id: Int): CollectionPreview?

}