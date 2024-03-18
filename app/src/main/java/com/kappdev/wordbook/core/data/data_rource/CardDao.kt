package com.kappdev.wordbook.core.data.data_rource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kappdev.wordbook.core.domain.model.Collection
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Query("SELECT COUNT(*) FROM cards WHERE collection_id = :collectionId")
    fun getCardsCount(collectionId: Int): Int

}