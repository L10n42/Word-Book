package com.kappdev.wordbook.main_feature.presentation.cards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kappdev.wordbook.core.domain.model.Card

class CardsViewModel: ViewModel() {

    var cards by mutableStateOf<List<Card>>(emptyList())
        private set

    init {
        cards = listOf(
            Card(
                id = 1,
                collectionId = 23,
                term = "dog",
                transcription = "/dɒɡ/",
                definition = "a common animal with four legs, especially kept by people as a pet or to hunt or guard things",
                example = "",
                image = "https://www.princeton.edu/sites/default/files/styles/1x_full_2x_half_crop/public/images/2022/02/KOA_Nassau_2697x1517.jpg?itok=Bg2K7j7J"
            ),
            Card(
                id = 2,
                collectionId = 23,
                term = "dog",
                transcription = "/dɒɡ/",
                definition = "a common animal with four legs, especially kept by people as a pet or to hunt or guard things",
                example = "We could hear dogs barking in the distance.",
                image = "https://www.princeton.edu/sites/default/files/styles/1x_full_2x_half_crop/public/images/2022/02/KOA_Nassau_2697x1517.jpg?itok=Bg2K7j7J"
            ),
            Card(
                id = 4,
                collectionId = 23,
                term = "dog",
                transcription = "/dɒɡ/",
                definition = "a common animal with four legs, especially kept by people as a pet or to hunt or guard things",
                example = "We could hear dogs barking in the distance.",
                image = "Wrong image"
            ),
            Card(
                id = 3,
                collectionId = 23,
                term = "dog",
                transcription = "/dɒɡ/",
                definition = "a common animal with four legs, especially kept by people as a pet or to hunt or guard things",
                example = "We could hear dogs barking in the distance.",
                image = null
            ),
            Card(
                id = 56,
                collectionId = 23,
                term = "dog",
                transcription = "/dɒɡ/",
                definition = "a common animal with four legs, especially kept by people as a pet or to hunt or guard things",
                example = "",
                image = null
            )
        )
    }

}