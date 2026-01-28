package com.education.myeye

import android.content.Context

class PreferenceManager(context: Context) {
    private val prefs = context.getSharedPreferences("myeye_prefs", Context.MODE_PRIVATE)

    fun saveBotToken(token: String) {
        prefs.edit().putString("bot_token", token.trim()).apply()
    }

    fun saveChatId(chatId: String) {
        prefs.edit().putString("chat_id", chatId.trim()).apply()
    }

    fun getBotToken(): String = prefs.getString("bot_token", "") ?: ""
    fun getChatId(): String = prefs.getString("chat_id", "") ?: ""
}

