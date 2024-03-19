package com.kappdev.wordbook.core.data.data_rource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kappdev.wordbook.core.domain.model.Card
import com.kappdev.wordbook.core.domain.model.Collection
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    @Query("SELECT * FROM cards WHERE card_id = :id LIMIT 1")
    suspend fun getCardById(id: Int): Card?

    @Query("DELETE FROM cards WHERE collection_id = :collectionId")
    suspend fun deleteCollectionCards(collectionId: Int)

    @Query("SELECT * FROM cards WHERE collection_id = :collectionId")
    fun getCollectionCards(collectionId: Int): Flow<List<Card>>

}