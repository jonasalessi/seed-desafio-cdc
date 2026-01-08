package com.challenge.state

import com.challenge.country.Country
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "states", uniqueConstraints = [UniqueConstraint(columnNames = ["name"], name = "uk_state_name")])
class State(
    @Column(nullable = false)
    @NotBlank
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    val country: Country
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is State) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
