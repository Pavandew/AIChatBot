package com.example.aistudent.model

data class MessageModel(
    val message: String?= null,  // Text content (nullable for voice )
    val role: String,
    val type: String = "text",
    val audio: String? = null // For voice Message
)