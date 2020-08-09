package com.sunny.chat.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "candidate")
data class Candidate private constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        @Column(nullable = false)
        var votingRoomId: Long,
        @Column(nullable = false)
        var name: String,
        @Column(name = "created_at")
        var createdAt: LocalDateTime
) {
    private constructor(votingRoomId: Long, name: String): this(null, votingRoomId, name, LocalDateTime.now())

    companion object {
        fun createVotingRoom(votingRoomId: Long, name: String): Candidate {
            return Candidate(votingRoomId, name)
        }
    }

    @PrePersist
    fun onPersist() {
        this.createdAt = LocalDateTime.now()
    }
}