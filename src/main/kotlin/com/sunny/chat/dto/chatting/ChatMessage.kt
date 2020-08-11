package com.sunny.chat.dto.chatting

import java.io.Serializable
import java.sql.Timestamp

class ChatMessage: Serializable {
    var productId: Long? = null
    var authorId: String? = null // same with author
    var author: String? = null
    var message: String? = null
    var messageType: ChatMessageType? = null
    var timestamp: String? = null

    constructor()

    constructor(productId: Long, author: String?, message: String, messageType: ChatMessageType) {
        this.productId = productId
        this.authorId = author
        this.author = author
        this.message = message
        this.messageType = messageType
    }
}