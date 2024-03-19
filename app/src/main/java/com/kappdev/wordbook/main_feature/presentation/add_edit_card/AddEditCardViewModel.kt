package com.kappdev.wordbook.main_feature.presentation.add_edit_card

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.domain.model.Card
import com.kappdev.wordbook.core.domain.util.DialogState
import com.kappdev.wordbook.core.domain.util.Result
import com.kappdev.wordbook.core.domain.util.mutableDialogStateOf
import com.kappdev.wordbook.main_feature.domain.use_case.GetCardById
import com.kappdev.wordbook.main_feature.domain.use_case.InsertCard
import com.kappdev.wordbook.main_feature.domain.util.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditCardViewModel @Inject constructor(
    private val insertCard: InsertCard,
    private val getCardById: GetCardById
) : ViewModel() {

    private var cardId: Int? = null
    private var cardCollection: Int? = null

    private var _loadingDialog = mutableDialogStateOf<Int?>(null)
    val loadingDialog: DialogState<Int?> = _loadingDialog

    var term by mutableStateOf("")
        private set

    var transcription by mutableStateOf("")
        private set

    var definition by mutableStateOf("")
        private set

    var example by mutableStateOf("")
        private set

    var cardImage by mutableStateOf<Image?>(null)
        private set


    fun getCard(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getCardById(id)?.unpack()
        }
    }

    fun saveAndAnother() = saveCard(onSuccess = ::resetCard)

    fun saveCard(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            packCard()?.let { packedCard ->
                _loadingDialog.showDialog(R.string.saving)
                val result = insertCard(packedCard, cardImage)
                _loadingDialog.hideDialog()
                when (result) {
                    is Result.Failure -> { /* TODO */ }
                    is Result.Success -> withContext(Dispatchers.Main) { onSuccess() }
                }
            }
        }
    }

    private fun packCard(): Card? {
        return cardCollection?.let { collectionId ->
            Card(cardId ?: 0, collectionId, term, transcription, definition, example, null)
        } /* TODO(Handle when collection doesn't selected) */
    }

    private fun Card.unpack() {
        cardId = this.id
        cardCollection = this.collectionId
        updateTerm(this.term)
        updateTranscription(this.transcription)
        updateDefinition(this.definition)
        updateExample(this.example)
        cardImage = this.image?.let(Image::Stored)
    }

    private fun resetCard() {
        cardId = null
        this.term = ""
        this.transcription = ""
        this.definition = ""
        this.example = ""
        cardImage = null
    }


    fun updateCollectionId(id: Int) {
        this.cardCollection = id
    }

    fun deleteImage() {
        this.cardImage = null
    }

    fun updateImage(uri: Uri) {
        this.cardImage = Image.NotStored(uri)
    }

    fun updateTerm(value: String) {
        this.term = value
    }

    fun updateTranscription(value: String) {
        this.transcription = value
    }

    fun updateDefinition(value: String) {
        this.definition = value
    }

    fun updateExample(value: String) {
        this.example = value
    }
}