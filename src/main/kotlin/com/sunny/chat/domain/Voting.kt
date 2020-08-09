package com.sunny.chat.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "voting_room")
data class Voting(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        @Column(nullable = false)
        var name: String,
        @Column
        var description: String?,
        @Column(name = "created_at")
        var createdAt: LocalDateTime
) {
    constructor(name: String, description: String?): this(null, name, description, LocalDateTime.now())

    companion object {
        fun createVotingRoom(name: String, description: String): Voting {
            return Voting(name, description)
        }
    }

    @PrePersist
    fun onPersist() {
        this.createdAt = LocalDateTime.now()
    }
}