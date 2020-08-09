package com.sunny.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChattingApplication

fun main(args: Array<String>) {
	runApplication<ChattingApplication>(*args)
}
