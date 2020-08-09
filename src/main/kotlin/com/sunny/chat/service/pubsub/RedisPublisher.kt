package com.sunny.chat.service.pubsub

import com.sunny.chat.dto.chatting.ChatMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service


@Service
class RedisPublisher {
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>

    fun publish(topic: ChannelTopic, message: ChatMessage) {
        redisTemplate.convertAndSend(topic.topic, message)
    }
}