package com.kappdev.wordbook.core.domain.util

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale

class TextToSpeechHelper(context: Context): TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech

    var availableLanguages by mutableStateOf(emptyList<Locale>())
        private set

    init {
        textToSpeech = TextToSpeech(context, this)
    }

    private fun getAvailableTTSLanguages(ttsEngine: TextToSpeech): List<Locale> {
        return Locale.getAvailableLocales().filter {
            ttsEngine.isLanguageAvailable(it) == TextToSpeech.LANG_COUNTRY_AVAILABLE
        }.sortedBy { it.displayName }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            availableLanguages = getAvailableTTSLanguages(this.textToSpeech)
        }
    }
}