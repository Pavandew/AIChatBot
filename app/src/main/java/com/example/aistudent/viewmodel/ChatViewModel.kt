package com.example.aistudent.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aistudent.Constants
import com.example.aistudent.model.MessageModel
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    // first create list
    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }


    // Don't create a new Chat each time inside sendMessage. Keep it at class level.
    private val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = Constants.API_KEY
    )

    private val chat: Chat = generativeModel.startChat()

    fun sendMessage(question: String) {
        Log.i("ChatViewModel", "User asked: $question")
        viewModelScope.launch {
            try {
                messageList.add(MessageModel(question, "user", type = "text"))
                // till loading we will show typing
                messageList.add(MessageModel("Analysing...", "model", type = "text"))

                val response = chat.sendMessage(question)
                messageList.removeAt(messageList.lastIndex) // this will remove the typing message when response will come
                messageList.add(MessageModel(response.text.toString(), "model", type = "text"))

                Log.i("ChatViewModel", "AI says: ${response.text.orEmpty()}")
            } catch (e: Exception) {
                messageList.removeAt(messageList.lastIndex)
                messageList.add(
                    MessageModel(
                        "Error: ${e.message.toString()}",
                        "model",
                        type = "text"
                    )
                )
                Log.e("ChatViewModel", "Error from Gemini API: ${e.message}", e)
            }
        }
    }

    fun addVoiceMessage(audioUri: String) {
        messageList.add(
            MessageModel(
                message = null,
                role = "user",
                type = "voice",
                audio = audioUri
            )
        )
    }
}