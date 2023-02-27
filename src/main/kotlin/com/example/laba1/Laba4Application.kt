package com.example.laba1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Laba4Application

    fun main(args: Array<String>) {
//        println(ClientServiceImpl().searchClientsByName("name"))
        runApplication<Laba4Application>(*args)
    }
