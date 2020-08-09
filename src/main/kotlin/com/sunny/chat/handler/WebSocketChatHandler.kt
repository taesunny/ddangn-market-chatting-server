package com.sunny.chat.handler

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketChatHandler : TextWebSocketHandler() {
    private val logger = KotlinLogging.logger {}

    @Throws(Exception::class)
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload: String = message.payload
        logger.info("payload {}", payload)

        val textMessage = TextMessage("Welcome chatting sever~^^")
        session.sendMessage(textMessage)
    }
}
