package com.kappdev.wordbook.main_feature.presentation.collections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo
import com.kappdev.wordbook.main_feature.domain.use_case.GetCollectionsInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val getCollectionsInfo: GetCollectionsInfo
) : ViewModel() {

    var collections by mutableStateOf<List<CollectionInfo>>(emptyList())
        private set

    private var collectionsJob: Job? = null

    fun getCollections() {
        collectionsJob?.cancel()
        collectionsJob = getCollectionsInfo().onEach { data ->
            collections = data
        }.launchIn(viewModelScope)
    }
}