package com.kappdev.wordbook.core.data.data_rource

import androidx.room.Dao
import androidx.room.Query
import com.kappdev.wordbook.core.domain.model.Card
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Query("DELETE FROM cards WHERE collection_id = :collectionId")
    suspend fun deleteCollectionCards(collectionId: Int)

    @Query("SELECT * FROM cards WHERE collection_id = :collectionId")
    fun getCollectionCards(collectionId: Int): Flow<List<Card>>

}