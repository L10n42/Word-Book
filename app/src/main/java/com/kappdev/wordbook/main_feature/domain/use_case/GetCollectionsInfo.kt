package com.kappdev.wordbook.main_feature.domain.use_case

import com.kappdev.wordbook.core.domain.repository.CollectionRepository
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionsInfo @Inject constructor(
    private val collectionRepository: CollectionRepository
) {

    operator fun invoke(): Flow<List<CollectionInfo>> {
        return collectionRepository.getCollectionsInfo()
    }
}