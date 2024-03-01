package com.kappdev.wordbook.core.domain.converter

import androidx.room.TypeConverter
import java.util.Locale

class LocaleConverter {

    @TypeConverter
    fun fromLocale(locale: Locale): String {
        return locale.toString()
    }

    @TypeConverter
    fun toLocale(localeString: String): Locale {
        return Locale.forLanguageTag(localeString)
    }
}