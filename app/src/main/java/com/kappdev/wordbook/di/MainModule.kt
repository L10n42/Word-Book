package com.kappdev.wordbook.di

import android.content.Context
import com.kappdev.wordbook.main_feature.data.repository.StorageRepositoryImpl
import com.kappdev.wordbook.main_feature.domain.repository.StorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideStorageRepository(@ApplicationContext context: Context): StorageRepository {
        return StorageRepositoryImpl(context)
    }

}