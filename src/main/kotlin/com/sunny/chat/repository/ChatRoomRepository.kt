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

@Repository
class ChatRoomRepository {
    @Autowired
    lateinit var redisMessageListener: RedisMessageListenerContainer
    @Autowired
    lateinit var redisSubscriber: RedisSubscriber
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>
    @Autowired
    lateinit var redisMessageTemplate: RedisTemplate<String, ChatMessage>
    lateinit var opsHashChatRoom: HashOperations<String, Long, ChatRoom>
    lateinit var opsHashChatMessage: ListOperations<String, ChatMessage>
    lateinit var topics: MutableMap<Long, ChannelTopic>

    @PostConstruct
    private fun init() {
        opsHashChatRoom = redisTemplate.opsForHash<Long, ChatRoom>()
        opsHashChatMessage = redisMessageTemplate.opsForList()
        topics = HashMap()
    }

    fun findAllRoom(): List<ChatRoom> {
        return opsHashChatRoom.values(CHAT_ROOMS)
    }

    fun findRoomById(id: Long): ChatRoom? {
        return opsHashChatRoom[CHAT_ROOMS, id]
    }

    fun createChatRoom(productId: Long): ChatRoom {
        val chatRoom: ChatRoom = ChatRoom(productId)
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.chatRoomId, chatRoom)
        return chatRoom
    }

    fun insertChatMessage(chatMessage: ChatMessage) {
//        opsHashChatMessage.add(CHAT_MESSAGE + chatMessage.productId, chatMessage, chatMessage.createdAt!!.time.toDouble())
        opsHashChatMessage.leftPush(CHAT_MESSAGE + chatMessage.productId, chatMessage)
        opsHashChatMessage.trim(CHAT_MESSAGE + chatMessage.productId, 0, CHAT_MESSAGE_MAX_SIZE-1)
    }

    fun findAllChatMessagesByProductId(productId: Long): List<Any>? {
//        val data: Set<ChatMessage>? = opsHashChatMessage.range(CHAT_MESSAGE + productId, 0, 10) as Set<ChatMessage>?
//        data ?: return ArrayList<ChatMessage>()
//        return data.toList()
        return opsHashChatMessage.range(CHAT_MESSAGE + productId, 0, 20)
    }

    fun enterChatRoom(roomId: Long) {
        if (!topics.containsKey(roomId)) {
            var topic = ChannelTopic(roomId.toString())
            redisMessageListener.addMessageListener(redisSubscriber, topic)
            topics[roomId] = topic
        }
    }

    fun getTopic(roomId: Long): ChannelTopic? {
        return topics[roomId]
    }

    companion object {
        private const val CHAT_ROOMS = "CHAT_ROOM"
        private const val CHAT_MESSAGE = "CHAT_MESSAGE"

        private const val CHAT_MESSAGE_MAX_SIZE: Long = 20
    }
}