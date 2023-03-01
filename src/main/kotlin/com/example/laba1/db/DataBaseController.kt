package com.example.laba1.db

import com.example.laba1.data.Client
import com.example.laba1.data.Goods
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class DataBaseController {

    private var connection: Connection? = null

    var clientList = mutableListOf<Client>()
    var goodsList = mutableListOf<Goods>()
    var orderList = mutableListOf<Goods>()

    init {

        try {
            connection = DriverManager.getConnection(urlConnect, dataBaseLogin, dataBasePassword)

            getAllClients()
            getAllGoods()
            getAllOrders()

            println(getAllClients())
        } catch (ignored: SQLException) {
            println("failed")
        }
    }


    @Throws(SQLException::class)
    fun getAllClients(): MutableList<Client> {

        val task = "select * from $clientTableName;"
        val resultSet = sendStatement(task, true) as ResultSet

        clientList = mutableListOf()

        while (resultSet.next()) {
            clientList += Client(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getLong(3),
                resultSet.getLong(4),
                resultSet.getLong(5),
                resultSet.getLong(6),
                resultSet.getLong(7),
                resultSet.getString(8)
            )
        }

        return clientList
    }

    fun getClientsNames(): MutableList<String> {
        val clientsNames = mutableListOf<String>()

        for (client in clientList) {
            clientsNames += client.name
        }

        return clientsNames

    }

    @Throws(SQLException::class)
    fun getAllGoods(): MutableList<Goods> {

        val task = "select * from $goodsTableName;"
        val resultSet = sendStatement(task, true) as ResultSet

        goodsList = mutableListOf()

        while (resultSet.next()) {
            goodsList += Goods(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
            )
        }

        return goodsList
    }

    fun addNewGoods(goods: Goods) {

        val task = "insert into $goodsTableName(naming, count, price)" +
                "values ('${goods.name}', ${goods.count}, ${goods.price});"
        sendStatement(task, false)

        getAllGoods()
        getAllOrders()
    }

    fun getAllOrders(): MutableList<Goods> {
        orderList = mutableListOf()
        orderList.addAll(goodsList)

        return orderList
    }

    fun updateClientTable(client: Client) {

        val task = "update client set " +
                "totalpurchase = ${client.totalPurchase}, " +
                "currentbalance = ${client.currentBalance}, " +
                "currentdebt = ${client.currentDebt}, " +
                "remainingdebt = ${client.remainingDebt} " +
                "where id = ${client.id};"

        sendStatement(task, false)

    }

    fun updateGoodsTable() {

        for (goods in goodsList) {

            val task = "update goods set " +
                    "count = ${goods.count} where id = ${goods.id};"

            sendStatement(task, false)

        }

    }



    @Throws(SQLException::class)
    fun sendStatement(task: String, isSelecting: Boolean): Any {
        val statement = connection!!.createStatement()
        return if (isSelecting) statement.executeQuery(task) else statement.executeUpdate(task)
    }

}