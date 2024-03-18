package com.kappdev.wordbook.main_feature.presentation.add_edit_collection

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.core.domain.model.Collection
import com.kappdev.wordbook.core.domain.util.DialogState
import com.kappdev.wordbook.core.domain.util.Result
import com.kappdev.wordbook.core.domain.util.ResultState
import com.kappdev.wordbook.core.domain.util.mutableDialogStateOf
import com.kappdev.wordbook.main_feature.domain.use_case.GetCollectionById
import com.kappdev.wordbook.main_feature.domain.use_case.InsertCollection
import com.kappdev.wordbook.main_feature.domain.util.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddEditCollectionViewModel @Inject constructor(
    private val insertCollection: InsertCollection,
    private val getCollectionById: GetCollectionById
) : ViewModel() {

    private var collectionId: Int? = null

    private var _loadingDialog = mutableDialogStateOf<String?>(null)
    val loadingDialogState: DialogState<String?> = _loadingDialog

    var name by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var termLanguage by mutableStateOf(Locale.UK)
        private set

    var definitionLanguage by mutableStateOf(Locale.UK)
        private set

    var cover by mutableStateOf<Image?>(null)
        private set

    var color by mutableStateOf<Color?>(null)
        private set

    fun getCollection(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val collection = getCollectionById(id)
            collection?.unpack()
        }
    }

    fun saveCollection(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingDialog.showDialog("Saving")
            val result = insertCollection(packCollection(), cover)
            _loadingDialog.hideDialog()
            when (result) {
                is Result.Failure -> { /* TODO */ }
                is Result.Success -> withContext(Dispatchers.Main) { onSuccess() }
            }
        }
    }

    private fun packCollection(): Collection {
        return Collection(collectionId ?: 0, name, description, termLanguage, definitionLanguage,null, color)
    }

    private fun Collection.unpack() {
        collectionId = this.id
        updateName(this.name)
        updateDescription(this.description)
        updateColor(this.color)
        updateTermLanguage(this.termLanguage)
        updateDefinitionLanguage(this.definitionLanguage)
        cover = this.backgroundImage?.let(Image::Stored)
    }



    fun updateColor(color: Color?) {
        this.color = color
    }

    fun removeCover() {
        this.cover = null
    }

    fun updateCover(uri: Uri) {
        this.cover = Image.NotStored(uri)
    }

    fun updateName(value: String) {
        this.name = value
    }

    fun updateDescription(value: String) {
        this.description = value
    }

    fun updateTermLanguage(value: Locale) {
        this.termLanguage = value
    }

    fun updateDefinitionLanguage(value: Locale) {
        this.definitionLanguage = value
    }
}