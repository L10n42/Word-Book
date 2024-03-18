package com.kappdev.wordbook.di

import android.content.Context
import androidx.room.Room
import com.kappdev.wordbook.core.data.data_rource.DictionaryDatabase
import com.kappdev.wordbook.core.data.repository.CardRepositoryImpl
import com.kappdev.wordbook.core.data.repository.CollectionRepositoryImpl
import com.kappdev.wordbook.core.domain.repository.CardRepository
import com.kappdev.wordbook.core.domain.repository.CollectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDictionaryDatabase(@ApplicationContext context: Context): DictionaryDatabase {
        return Room.databaseBuilder(context, DictionaryDatabase::class.java, DictionaryDatabase.NAME).build()
    }

    @Provides
    @Singleton
    fun provideCollectionRepository(db: DictionaryDatabase): CollectionRepository {
        return CollectionRepositoryImpl(db.getCollectionDao())
    }

    @Provides
    @Singleton
    fun provideCardRepository(db: DictionaryDatabase): CardRepository {
        return CardRepositoryImpl(db.getCardDao())
    }

}