package com.sunny.chat.service
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.sunny.votingandchatting.domain.Voting
//import mu.KotlinLogging
//import org.springframework.stereotype.Service
//import org.springframework.web.socket.TextMessage
//import org.springframework.web.socket.WebSocketSession
//import java.io.IOException
//import java.time.LocalDateTime
//import java.util.*
//import javax.annotation.PostConstruct
//
//@Service
//class ChatService {
//    private val objectMapper: ObjectMapper? = null
//    private var votingRooms: MutableMap<Long, Voting>? = null
//    private val logger = KotlinLogging.logger {}
//
//    @PostConstruct
//    private fun init() {
//        votingRooms = LinkedHashMap<Long, Voting>()
//    }
//
//    fun findAllRoom(): List<Voting?> {
//        return ArrayList<Voting?>(votingRooms!!.values)
//    }
//
//    fun findVotingRoomById(votingRoomId: Long): Voting? {
//        return votingRooms!![votingRoomId]
//        return chatRoom
//    }
//
//    fun <T> sendMessage(session: WebSocketSession, message: T) {
//        try {
//            session.sendMessage(TextMessage(objectMapper!!.writeValueAsString(message)))
//        } catch (e: IOException) {
//            logger.error(e.message, e)
//        }
//    }

//    }
//
//    fun createRoom(name: String, description: String?): Voting {
//        var randomId: Long  = Random().nextLong()
//
//        // temp
//        val chatRoom: Voting = Voting(randomId, name, description, LocalDateTime.now())
//        votingRooms!![randomId] = chatRoom}