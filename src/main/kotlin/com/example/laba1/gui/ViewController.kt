package com.example.laba1.gui

import com.example.laba1.data.Client
import com.example.laba1.data.Goods
import com.example.laba1.data.Order
import com.example.laba1.db.DataBaseController
import com.example.laba1.db.sellTypes
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.KeyEvent

class ViewController {

    lateinit var dbController: DataBaseController



    @FXML
    private lateinit var clientSelector: ChoiceBox<String>

    @FXML
    private lateinit var clientTable: TableView<Client>

    @FXML
    private lateinit var commentaryClientTable: TableColumn<Client, String>

    @FXML
    private lateinit var countGoodsTable: TableColumn<Goods, Int>

    @FXML
    private lateinit var countInStorageOrderTable: TableColumn<Order, Int>

    @FXML
    private lateinit var createOrder: Button

    @FXML
    private lateinit var creditLimitClientTable: TableColumn<Client, Long>

    @FXML
    private lateinit var currentBalanceClientTable: TableColumn<Client, Long>

    @FXML
    private lateinit var currentDebtClientTable: TableColumn<Client, Long>

    @FXML
    private lateinit var goodsTable: TableView<Goods>

    @FXML
    private lateinit var nameClientTable: TableColumn<Client, String>

    @FXML
    private lateinit var nameGoodsTable: TableColumn<Goods, String>

    @FXML
    private lateinit var nameOrderTable: TableColumn<Order, String>

    @FXML
    private lateinit var orderTable: TableView<Order>

    @FXML
    private lateinit var priceGoodsTable: TableColumn<Goods, Int>

    @FXML
    private lateinit var priceOrderTable: TableColumn<Order, Int>

    @FXML
    private lateinit var remainingDebtClientTable: TableColumn<Client, Long>

    @FXML
    private lateinit var searchClient: TextField

    @FXML
    private lateinit var sellTypeSelector: ChoiceBox<String>

    @FXML
    private lateinit var totalOrderTable: TableColumn<Order, Long>

    @FXML
    private lateinit var totalPurchaseClientTable: TableColumn<Client, Long>

    @FXML
    private lateinit var wantedCountOrderTable: TableColumn<Order, Int>


    @FXML
    private fun initialize() {
        dbController = DataBaseController()

        initClientTable()
        initGoodsTable()

        initClientSelector()
        initSellTypeSelector()







    }

    @FXML
    fun onClientSearchTextChanged(event: KeyEvent) {
        println("text changed: ${searchClient.text}")

        clientTable.items.removeAll(clientTable.items)

        with(searchClient.text) {

            if (this.isEmpty()) {
                clientTable.items.addAll(dbController.clientList)

            } else {
                val filteredClientList = mutableListOf<Client>()

                for (client in dbController.clientList) {
                    if (client.name.startsWith(this, true)) {
                        filteredClientList += client
                    }
                }

                clientTable.items.addAll(filteredClientList)
            }
        }

    }

    @FXML
    fun onCreateOrder(event: ActionEvent) {

    }

    private fun initClientTable() {
        nameClientTable.cellValueFactory = PropertyValueFactory("name")
        totalPurchaseClientTable.cellValueFactory = PropertyValueFactory("totalPurchase")
        currentBalanceClientTable.cellValueFactory = PropertyValueFactory("currentBalance")
        creditLimitClientTable.cellValueFactory = PropertyValueFactory("creditLimit")
        currentDebtClientTable.cellValueFactory = PropertyValueFactory("currentDebt")
        remainingDebtClientTable.cellValueFactory = PropertyValueFactory("remainingDebt")
        commentaryClientTable.cellValueFactory = PropertyValueFactory("commentary")

        clientTable.items = FXCollections.observableArrayList(dbController.clientList)

    }

    private fun initGoodsTable() {
        nameGoodsTable.cellValueFactory = PropertyValueFactory("name")
        countGoodsTable.cellValueFactory = PropertyValueFactory("count")
        priceGoodsTable.cellValueFactory = PropertyValueFactory("price")

        goodsTable.items = FXCollections.observableArrayList(dbController.goodsList)
    }

    private fun initClientSelector() {
        clientSelector.items.addAll(dbController.getClientsNames())
    }


    private fun initSellTypeSelector() {
        sellTypeSelector.items.addAll(sellTypes)
    }


}
