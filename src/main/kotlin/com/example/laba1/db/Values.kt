package com.example.laba1.db

const val clientTableName = "client"
const val goodsTableName = "goods"
const val orderTableName = "orders"
const val orderGoodsTableName = "order_goods"
const val dataBaseLogin = "postgres"
const val dataBasePassword = "1965"

const val urlConnect = "jdbc:postgresql://localhost:5432/postgres"

val sellTypes = listOf("Наличный расчет", "Безналичный расчет", "Кредит", "Бартер", "Взаимозачет")
