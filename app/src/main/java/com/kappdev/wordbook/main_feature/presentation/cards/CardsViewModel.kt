package com.kappdev.wordbook.main_feature.presentation.cards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.core.domain.model.Card
import com.kappdev.wordbook.core.domain.util.TextToSpeechHelper
import com.kappdev.wordbook.main_feature.domain.model.CollectionPreview
import com.kappdev.wordbook.main_feature.domain.use_case.DeleteCardById
import com.kappdev.wordbook.main_feature.domain.use_case.GetCollectionCards
import com.kappdev.wordbook.main_feature.domain.use_case.GetCollectionLanguage
import com.kappdev.wordbook.main_feature.domain.use_case.GetCollectionName
import com.kappdev.wordbook.main_feature.domain.use_case.GetCollectionsPreview
import com.kappdev.wordbook.main_feature.domain.use_case.MoveCardTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCollectionsPreview: GetCollectionsPreview,
    private val getCollectionCards: GetCollectionCards,
    private val getCollectionName: GetCollectionName,
    private val getCollectionLanguage: GetCollectionLanguage,
    private val deleteCardById: DeleteCardById,
    private val moveCardTo: MoveCardTo,
    private val textToSpeechHelper: TextToSpeechHelper
) : ViewModel() {

    private var collectionLanguage: Locale? = null

    var collections by mutableStateOf<List<CollectionPreview>>(emptyList())
        private set

    var collectionName by mutableStateOf<String?>(null)
        private set

    var cards by mutableStateOf<List<Card>>(emptyList())
        private set

    private var cardsJob: Job? = null
    private var collectionsJob: Job? = null

    init {
        getCollections()
    }

    fun speak(text: String) {
        collectionLanguage?.let { language ->
            textToSpeechHelper.say(text, language)
        }
    }

    fun getCards(collectionId: Int) {
        cardsJob?.cancel()
        cardsJob = getCollectionCards(collectionId).onEach(::updateCards).launchIn(viewModelScope)
    }

    fun getCollectionInfo(collectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionName = getCollectionName(collectionId)
            collectionLanguage = getCollectionLanguage(collectionId)
        }
    }

    fun deleteCard(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCardById(id)
        }
    }

    fun moveTo(cardId: Int, newCollectionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            moveCardTo(cardId, newCollectionId)
        }
    }

    private fun getCollections() {
        collectionsJob?.cancel()
        collectionsJob = getCollectionsPreview().onEach{ collections = it }.launchIn(viewModelScope)
    }

    private fun updateCards(data: List<Card>) {
        this.cards = data
    }

}