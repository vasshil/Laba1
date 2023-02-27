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

    init {

        try {
            connection = DriverManager.getConnection(urlConnect, dataBaseLogin, dataBasePassword)

            getAllClients()
            getAllGoods()

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


//    fun getUserRole(username: String, password: String): Roles {
//        val task = "select job from \"user\" where username = '$username' and password = '$password';"
//        val result = sendStatement(task, true) as ResultSet
//        while (result.next()) {
//            return Roles.values()[result.getInt("job")]
//        }
//
//        return Roles.NONE
//
//    }
//
//    fun deleteUserById(id: Int) {
//        val task = "delete from \"user\" where id = $id;"
//        sendStatement(task, false)
//    }
//
//    fun updateUserById(id: Int, user: UserData) {
//        val task = "update \"user\" set fullName = '${user.fullName}', job = '${user.job}', address = '${user.address}', phoneNumber = '${user.phoneNumber}', username = '${user.username}', password = '${user.password}' where id = $id;"
//        sendStatement(task, false)
//    }
//
//    fun addNewUser(user: UserData) {
//        val task = "insert into \"user\"(fullName, job, address, phoneNumber, username, password)" +
//                "values ('${user.fullName}', ${user.job}, '${user.address}', '${user.phoneNumber}', '${user.username}', '${user.password}');"
//        sendStatement(task, false)
//    }

    @Throws(SQLException::class)
    fun sendStatement(task: String, isSelecting: Boolean): Any {
        val statement = connection!!.createStatement()
        return if (isSelecting) statement.executeQuery(task) else statement.executeUpdate(task)
    }

}