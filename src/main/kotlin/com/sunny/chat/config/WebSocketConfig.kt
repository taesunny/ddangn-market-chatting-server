package com.sunny.chat.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig: WebSocketMessageBrokerConfigurer {

//    @Bean
//    fun httpHandshakeInterceptor(): HttpHandshakeInterceptor {
//        return HttpHandshakeInterceptor()
//    }

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/sub")
        config.setApplicationDestinationPrefixes("/pub")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/chatting").setAllowedOrigins("http://ddangn.taesunny.com")
//                .addInterceptors(httpHandshakeInterceptor())
                .withSockJS()
    }
}