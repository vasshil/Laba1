package com.example.laba1.repository

import com.example.laba1.data.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository: JpaRepository<Client, Long> {
//
//    @Query("select c from Client c where lower(c.name) like lower(:name)")
//    fun searchClientsByName(@Param("name") name: String): List<Client>

}