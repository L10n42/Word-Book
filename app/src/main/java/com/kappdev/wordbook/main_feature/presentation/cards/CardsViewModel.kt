package com.kappdev.wordbook.main_feature.presentation.cards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.core.domain.model.Card
import com.kappdev.wordbook.main_feature.domain.use_case.GetCollectionCards
import com.kappdev.wordbook.main_feature.domain.use_case.GetCollectionName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCollectionCards: GetCollectionCards,
    private val getCollectionName: GetCollectionName
) : ViewModel() {

    var collectionName by mutableStateOf<String?>(null)
        private set

    var cards by mutableStateOf<List<Card>>(emptyList())
        private set

    private var cardsJob: Job? = null

    fun getCards(collectionId: Int) {
        cardsJob?.cancel()
        cardsJob = getCollectionCards(collectionId).onEach(::updateCards).launchIn(viewModelScope)
    }

    fun getTitle(collectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionName = getCollectionName(collectionId)
        }
    }

    private fun updateCards(data: List<Card>) {
        this.cards = data
    }

}