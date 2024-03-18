package com.kappdev.wordbook.main_feature.presentation.add_edit_card

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddEditCardViewModel : ViewModel() {

    var term by mutableStateOf("")
        private set

    var transcription by mutableStateOf("")
        private set

    var definition by mutableStateOf("")
        private set

    var example by mutableStateOf("")
        private set



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