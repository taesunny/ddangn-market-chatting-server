package com.sunny.chat.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebMvcConfig : WebMvcConfigurer {
//    private val MAX_AGE_SECS: Long = 3600
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.GET.name,
                        HttpMethod.HEAD.name,
                        HttpMethod.POST.name,
                        HttpMethod.PUT.name,
                        HttpMethod.DELETE.name,
                        HttpMethod.PATCH.name,
                        HttpMethod.OPTIONS.name)
    }
}