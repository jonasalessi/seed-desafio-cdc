package com.challenge.state

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StateRepository : CrudRepository<State, Long> {
    fun existsByNameIgnoreCase(name: String): Boolean
}
