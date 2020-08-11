package com.sunny.chat.controller

import com.sunny.chat.dto.chatting.ChatMessage
import com.sunny.chat.repository.ChatRoomRepository
import com.sunny.chat.service.pubsub.RedisPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.util.stream.Collectors

@CrossOrigin
@RestController
class ChatController {
    @Autowired
    lateinit var redisPublisher: RedisPublisher
    @Autowired
    private val chatRoomRepository: ChatRoomRepository? = null

    @MessageMapping("/chat/message")
    fun message(message: ChatMessage) {
        message.timestamp = Instant.now().toEpochMilli().toString()

//        if(ChatMessageType.ENTER == message.messageType) {
//            chatRoomRepository!!.enterChatRoom(message.productId)
//            return
//        }

        // add to history
        chatRoomRepository!!.setTopic(message.productId!!)

        chatRoomRepository!!.insertChatMessage(message)

        chatRoomRepository.getTopic(message.productId!!)?.let { redisPublisher.publish(it, message) }
    }

    @GetMapping("/products/{productId}/history")
    fun getChattingHistory(@PathVariable productId: Long): List<ChatMessage> {
        println("history!")
        val data: List<ChatMessage> = chatRoomRepository!!.findAllChatMessagesByProductId(productId) as List<ChatMessage>
                ?: return ArrayList<ChatMessage>()

        println("data size: " + data.size)

        return data.sortedBy { item -> item.timestamp }
    }
}