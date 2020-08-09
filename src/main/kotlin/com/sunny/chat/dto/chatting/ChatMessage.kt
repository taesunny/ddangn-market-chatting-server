package com.sunny.chat.dto.chatting

import java.io.Serializable
import java.sql.Timestamp

class ChatMessage(productId: Long, userId: Long?, userEmail: String?, message: String, messageType: ChatMessageType): Serializable {
    var productId: Long = productId
    var userId: Long? = userId
    var userEmail: String? = userEmail
    var message: String = message
    var messageType: ChatMessageType = messageType
    var createdAt: Timestamp? = null
}