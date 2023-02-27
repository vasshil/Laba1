package com.example.laba1.data

data class Goods(
    val id: Long,
    var name: String,
    var count: Int,
    var price: Int,
    var wantedCountOrder: String = "0",
    var total: Long = 0
)
