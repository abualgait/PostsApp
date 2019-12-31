package com.abualgait.msayed.thiqah.shared.util


import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
class LanguagePrfs(context: Activity, isEnglish: Boolean, var sharedPreferences: SharedPref) {

    private var context: Activity? = context

    init {
        if (isEnglish) {
            saveLanguage("en")
            initRTL("en")
        } else {
            saveLanguage("ar")
            initRTL("ar")
        }

    }

    fun saveLanguage(language: String) {
        try {
            sharedPreferences.lang = language
        } catch (exc: Exception) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun initRTL(lang: String) {
        if (lang.equals("en", ignoreCase = true)) {

            val resources = context!!.applicationContext.resources
            // work for > 21
            val languageToLoad = "en"
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            context!!.resources.updateConfiguration(config, context!!.resources.displayMetrics)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                config.setLayoutDirection(locale)
                resources.updateConfiguration(config, null)

            }

        } else {

            val resources = context!!.applicationContext.resources
            val languageToLoad = "ar"
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            context!!.resources.updateConfiguration(config, context!!.resources.displayMetrics)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                config.setLayoutDirection(locale)
                resources.updateConfiguration(config, null)
            }
        }
    }

    companion object {
        fun getLanguage(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SharedPref.Key.PREF_LANG.name, "en")
        }
    }

}