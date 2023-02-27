package com.example.laba1.service

import com.example.laba1.data.Client

interface ClientService {

    fun searchClientsByName(name: String): List<Client>

}