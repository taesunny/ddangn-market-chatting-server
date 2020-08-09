package com.sunny.chat.controller

import com.sunny.chat.dto.chatting.ChatMessage
import com.sunny.chat.repository.ChatRoomRepository
import com.sunny.chat.service.pubsub.RedisPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.sql.Timestamp


@Controller
class ChatController {
    @Autowired
    lateinit var redisPublisher: RedisPublisher
    @Autowired
    private val chatRoomRepository: ChatRoomRepository? = null

    @MessageMapping("/chat/message")
    fun message(message: ChatMessage) {
        message.createdAt = Timestamp(System.currentTimeMillis())

//        if(ChatMessageType.ENTER == message.messageType) {
//            chatRoomRepository!!.enterChatRoom(message.productId)
//            return
//        }

        // add to history

        if (chatRoomRepository!!.findRoomById(message.productId) == null) {
            chatRoomRepository!!.enterChatRoom(message.productId)
        }

        chatRoomRepository!!.insertChatMessage(message)

        chatRoomRepository!!.getTopic(message.productId)?.let { redisPublisher.publish(it, message) }
    }

    @RequestMapping("/products/{productId}/history")
    @Throws(Exception::class)
    fun getChattingHistory(@PathVariable productId: Long): List<Any>? {
        println("history!")

        return chatRoomRepository!!.findAllChatMessagesByProductId(productId)
    }
}