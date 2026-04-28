package com.example.seminarapp

import android.content.Context

object SessionManager {

    private const val PREF_NAME = "session"
    private const val KEY_LOGIN = "isLogin"
    private const val KEY_NAME = "name"
    private const val KEY_EMAIL = "email"

    fun saveLogin(context: android.content.Context, name: String, email: String) {
        val pref = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        pref.edit().apply {
            putBoolean(KEY_LOGIN, true)
            putString(KEY_NAME, name)
            putString(KEY_EMAIL, email)
            apply()
        }
    }

    fun isLogin(context: android.content.Context): Boolean {
        val pref = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        return pref.getBoolean(KEY_LOGIN, false)
    }

    fun logout(context: android.content.Context) {
        val pref = context.getSharedPreferences(PREF_NAME, android.content.Context.MODE_PRIVATE)
        pref.edit().clear().apply()
    }

    fun getName(context: Context): String {
        val pref = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        return pref.getString("name", "User") ?: "User"
    }

}