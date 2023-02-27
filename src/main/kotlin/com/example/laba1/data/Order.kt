package com.example.laba1.data

class Order(
    val id: Long,
    var clientId: Long,
    var paymentType: PaymentType,
    var wantedCountOrder: Int = 0,
    var total: Long
) {
}