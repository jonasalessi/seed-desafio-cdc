package com.challenge.author

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.Instant

@Entity
@Table(name = "authors", uniqueConstraints = [UniqueConstraint(columnNames = ["email"], name = "uk_author_email")])
class Author(
    @Column(nullable = false)
    @NotBlank
    val name: String,

    @Column(nullable = false)
    @NotBlank
    val email: String,

    @Column(nullable = false, length = 400)
    @NotBlank
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
