package com.education.myeye

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferenceManager(this)

        val etToken = findViewById<EditText>(R.id.etBotToken)
        val etChatId = findViewById<EditText>(R.id.etChatId)
        val etMessage = findViewById<EditText>(R.id.etMessage)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)

        // Load saved values
        etToken.setText(prefs.getBotToken())
        etChatId.setText(prefs.getChatId())

        btnSave.setOnClickListener {
            prefs.saveBotToken(etToken.text.toString())
            prefs.saveChatId(etChatId.text.toString())
            tvStatus.text = "Saved ✅"
        }

        btnSend.setOnClickListener {
            tvStatus.text = "Sending..."
            val token = etToken.text.toString()
            val chatId = etChatId.text.toString()
            val message = etMessage.text.toString()

            thread {
                val result = TelegramService.sendMessage(token, chatId, message)
                runOnUiThread {
                    tvStatus.text = result.fold(
                        onSuccess = { "Sent ✅\n${'$'}it" },
                        onFailure = { "Failed ❌\n${'$'}{it.message ?: it.toString()}" }
                    )
                }
            }
        }
    }
}

