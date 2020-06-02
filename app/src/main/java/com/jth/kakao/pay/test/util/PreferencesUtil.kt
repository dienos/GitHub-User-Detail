package com.jth.kakao.pay.test.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun set(key : String, value : Any) {
        when(value) {
            is String -> preferences.edit().putString(key, value).apply()
        }
    }

    fun getString(key : String) = preferences.getString(key, "") ?: ""
}