package com.challenge.country

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : CrudRepository<Country, Int> {
    fun existsByNameIgnoreCase(name: String): Boolean
}
