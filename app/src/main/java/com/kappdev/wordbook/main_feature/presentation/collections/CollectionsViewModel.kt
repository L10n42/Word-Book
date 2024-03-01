package com.kappdev.wordbook.main_feature.presentation.collections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kappdev.wordbook.main_feature.domain.model.CollectionInfo

class CollectionsViewModel : ViewModel() {

    var collections by mutableStateOf<List<CollectionInfo>>(emptyList())
        private set

    init {
        collections = listOf(
            CollectionInfo(1, "Animals", "A collection of common animals", 32),
            CollectionInfo(2, "Fruits", "A collection of common fruits", 21),
            CollectionInfo(3, "Body Parts", "A collection of main body parts", 18)
        )
    }
}