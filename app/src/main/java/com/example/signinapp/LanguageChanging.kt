package com.example.signinapp

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat



fun setAppLanguage(languageCode: String) {
    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
    AppCompatDelegate.setApplicationLocales(appLocale)
}

