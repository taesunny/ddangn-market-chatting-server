package com.sunny.chat.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import redis.embedded.RedisServer;

@Profile("local")
@Configuration
class EmbeddedRedisConfig {
    @Value("\${spring.redis.port}")
    private val redisPort = 0
    private var redisServer: RedisServer? = null

    @PostConstruct
    fun redisServer() {
        redisServer = RedisServer(redisPort)
        redisServer?.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer?.stop()
    }
}
