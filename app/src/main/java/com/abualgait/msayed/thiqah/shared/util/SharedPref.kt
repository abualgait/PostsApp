package com.abualgait.msayed.thiqah.shared.util

import android.content.SharedPreferences


class SharedPref(private val pref: SharedPreferences) {

    private var editor: SharedPreferences.Editor = pref.edit()

    private fun putString(value: String?, key: Key) {
        editor.putString(key.name, value)
        editor.apply()
    }

    private fun getString(key: Key, def: String): String? {
        return pref.getString(key.name, def)
    }


    fun clear() {
        editor.clear()
        editor.apply()
    }

    enum class Key {
        LANG,
        PREF_LANG
    }

    var lang: String
        get() = getString(Key.LANG, "en")!!
        set(value) {
            putString(value, Key.LANG)
        }


}
