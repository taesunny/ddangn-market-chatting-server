package com.sunny.chat.service.pubsub

import com.fasterxml.jackson.databind.ObjectMapper
import com.sunny.chat.dto.chatting.ChatMessage
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessageSendingOperations

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service

@Service
class RedisSubscriber: MessageListener{
    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>
    @Autowired
    lateinit var messagingTemplate: SimpMessageSendingOperations
    private val logger = KotlinLogging.logger {}

    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            // deserialize
            val publishMessage = redisTemplate.stringSerializer.deserialize(message.body) as String

            // map to productMessage
            val productMessage = objectMapper!!.readValue(publishMessage, ChatMessage::class.java)

            // send message
            messagingTemplate.convertAndSend("/sub/chat/product/" + productMessage.productId, productMessage)
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }

}