package com.education.myeye

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

object TelegramService {
    private val client = OkHttpClient()

    /**
     * Safe demo: Sends a user-entered message to a user-entered chatId.
     * NOTE: Only use with your own bot and with explicit user consent.
     */
    fun sendMessage(botToken: String, chatId: String, text: String): Result<String> {
        if (botToken.isBlank()) return Result.failure(IllegalArgumentException("Bot token is empty"))
        if (chatId.isBlank()) return Result.failure(IllegalArgumentException("Chat ID is empty"))
        if (text.isBlank()) return Result.failure(IllegalArgumentException("Message is empty"))

        val url = "https://api.telegram.org/bot${'$'}botToken/sendMessage"

        val body = FormBody.Builder()
            .add("chat_id", chatId)
            .add("text", text)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        return try {
            client.newCall(request).execute().use { resp ->
                val respBody = resp.body?.string().orEmpty()
                if (!resp.isSuccessful) {
                    Result.failure(IllegalStateException("HTTP ${'$'}{resp.code}: ${'$'}respBody"))
                } else {
                    Result.success(respBody)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

