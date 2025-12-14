package com.challenge.category

import jakarta.persistence.*

@Entity
@Table(name = "categories", uniqueConstraints = [UniqueConstraint(columnNames = ["name"])])
class Category(
    @Column(nullable = false)
    val name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Category) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }


}