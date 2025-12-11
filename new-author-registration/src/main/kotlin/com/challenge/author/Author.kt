package com.challenge.author

import jakarta.persistence.*
import java.time.Instant

@Entity
class Author(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false, length = 400)
    val description: String,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
