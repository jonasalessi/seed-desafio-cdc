package com.challenge.country

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "countries", uniqueConstraints = [UniqueConstraint(columnNames = ["name"], name = "uk_country_name")])
class Country(
    @Column(nullable = false)
    @NotBlank
    val name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Country) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
