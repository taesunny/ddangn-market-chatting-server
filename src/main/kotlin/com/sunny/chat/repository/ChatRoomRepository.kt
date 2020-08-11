package com.sunny.chat.repository

import com.sunny.chat.dto.chatting.ChatMessage
import com.sunny.chat.dto.chatting.ChatRoom
import com.sunny.chat.service.pubsub.RedisSubscriber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.ListOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Repository
import java.util.*
import javax.annotation.PostConstruct
import kotlin.collections.ArrayList

@Repository
class ChatRoomRepository {
    @Autowired
    lateinit var redisMessageListener: RedisMessageListenerContainer
    @Autowired
    lateinit var redisSubscriber: RedisSubscriber
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>
    lateinit var opsListChatMessage: ListOperations<String, Any>
//    lateinit var opsHashChatMessage: ListOperations<String, ChatMessage>
    lateinit var topics: MutableMap<Long, ChannelTopic>

    @PostConstruct
    private fun init() {
        opsListChatMessage = redisTemplate.opsForList()
        topics = HashMap()
    }

//    fun findAllRoom(): List<ChatRoom> {
//        return opsHashChatRoom.values(CHAT_ROOMS)
//    }
//
//    fun findRoomById(id: Long): ChatRoom? {
//        return opsHashChatRoom[CHAT_ROOMS, id]
//    }
//
//    fun createChatRoom(productId: Long): ChatRoom {
//        val chatRoom: ChatRoom = ChatRoom(productId)
//        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.chatRoomId, chatRoom)
//        return chatRoom
//    }

    fun insertChatMessage(chatMessage: ChatMessage) {
//        opsHashChatMessage.add(CHAT_MESSAGE + chatMessage.productId, chatMessage, chatMessage.createdAt!!.time.toDouble())
        opsListChatMessage.leftPush(CHAT_MESSAGE + chatMessage.productId, chatMessage)
        opsListChatMessage.trim(CHAT_MESSAGE + chatMessage.productId, 0, CHAT_MESSAGE_MAX_SIZE-1)
    }

    fun findAllChatMessagesByProductId(productId: Long): List<Any>? {
//        val data: Set<ChatMessage>? = opsHashChatMessage.range(CHAT_MESSAGE + productId, 0, 10) as Set<ChatMessage>?
//        data ?: return ArrayList<ChatMessage>()
//        return data.toList()

        return opsListChatMessage.range(CHAT_MESSAGE + productId, 0, 20)
    }

    fun setTopic(productId: Long) {
        if (!topics.containsKey(productId)) {
            var topic = ChannelTopic(productId.toString())
            redisMessageListener.addMessageListener(redisSubscriber, topic)
            topics[productId] = topic
        }
    }

    fun getTopic(productId: Long): ChannelTopic? {
        return topics[productId]
    }

    companion object {
        private const val CHAT_ROOMS = "CHAT_ROOM"
        private const val CHAT_MESSAGE = "CHAT_MESSAGE"

        private const val CHAT_MESSAGE_MAX_SIZE: Long = 20
    }
}