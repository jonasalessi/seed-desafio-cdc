package com.challenge.author

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "authors", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Author) return false

        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }
}
