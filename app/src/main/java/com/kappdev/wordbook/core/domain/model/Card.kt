package com.kappdev.wordbook.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "collection_id")
    val collectionId: Int,

    @ColumnInfo(name = "term")
    val term: String,

    @ColumnInfo(name = "transcription")
    val transcription: String,

    @ColumnInfo(name = "definition")
    val definition: String,

    @ColumnInfo(name = "example")
    val example: String,

    @ColumnInfo(name = "image")
    val image: String?
)