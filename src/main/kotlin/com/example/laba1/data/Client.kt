package com.example.laba1.data

//import org.springframework.data.annotation.Id
//import javax.persistence.Column
//import javax.persistence.Entity
//import javax.persistence.GeneratedValue
//import javax.persistence.Table
//
//
//@Entity
//@Table(name = "client")
//data class Client(
//
//    @javax.persistence.Id @GeneratedValue @Id
//    @Column(name = "id", nullable = false)
//    val id: Long? = null,
//
//    @Column(name = "name", nullable = false)
//    val name: String? = null, // имя
//
//    @Column(name = "totalPurchase", nullable = false)
//    var totalPurchase: Long? = null, // общий счет покупок
//
//    @Column(name = "currentBalance", nullable = false)
//    var currentBalance: Long? = null, // текущий баланс на счету
//
//    @Column(name = "creditLimit", nullable = false)
//    val creditLimit: Long? = null, // потолок кредита
//
//    @Column(name = "currentDebt", nullable = false)
//    var currentDebt: Long? = null, // текущий долг
//
//    @Column(name = "remainingDebt", nullable = false)
//    var remainingDebt: Long? = null, // остаток по кредиту
//
//    @Column(name = "commentary", nullable = false)
//    var commentary: String? = null // комментарий
//
//)


class Client(
    val id: Long,
    var name: String, // имя
    var totalPurchase: Long, // общий счет покупок
    var currentBalance: Long, // текущий баланс на счету
    var creditLimit: Long, // потолок кредита
    var currentDebt: Long, // текущий долг
    var remainingDebt: Long, // остаток по кредиту
    var commentary: String // комментарий

) {

    fun countRemainingDebt() {
        remainingDebt = creditLimit - currentDebt
    }

}



