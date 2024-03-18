package com.kappdev.wordbook.core.data.data_rource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kappdev.wordbook.core.domain.converter.ColorConverter
import com.kappdev.wordbook.core.domain.converter.LocaleConverter
import com.kappdev.wordbook.core.domain.model.Card
import com.kappdev.wordbook.core.domain.model.Collection

@Database(
    entities = [Card::class, Collection::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(LocaleConverter::class, ColorConverter::class)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun getCardDao(): CardDao
    abstract fun getCollectionDao(): CollectionDao

    companion object {
        const val NAME = "dictionary_database"
    }

}