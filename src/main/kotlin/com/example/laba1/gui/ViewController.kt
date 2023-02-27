package com.example.laba1.gui

import com.example.laba1.data.Client
import com.example.laba1.data.Goods
import com.example.laba1.db.DataBaseController
import com.example.laba1.db.sellTypes
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.input.KeyEvent
import javafx.util.Callback

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
    private lateinit var countInStorageOrderTable: TableColumn<Goods, Int>

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
    private lateinit var nameOrderTable: TableColumn<Goods, String>

    @FXML
    private lateinit var orderTable: TableView<Goods>

    @FXML
    private lateinit var priceGoodsTable: TableColumn<Goods, Int>

    @FXML
    private lateinit var priceOrderTable: TableColumn<Goods, Int>

    @FXML
    private lateinit var remainingDebtClientTable: TableColumn<Client, Long>

    @FXML
    private lateinit var searchClient: TextField

    @FXML
    private lateinit var sellTypeSelector: ChoiceBox<String>

    @FXML
    private lateinit var totalOrderTable: TableColumn<Goods, Long>

    @FXML
    private lateinit var totalPurchaseClientTable: TableColumn<Client, Long>

    @FXML
    private lateinit var wantedCountOrderTable: TableColumn<Goods, String>

    @FXML
    private lateinit var selectedClientInfo: Label


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

    private fun initOrderTable() {
        if (clientSelector.value != null && sellTypeSelector.value != null) {

            dbController.getAllGoods()

            nameOrderTable.cellValueFactory = PropertyValueFactory("name")
            countInStorageOrderTable.cellValueFactory = PropertyValueFactory("count")
            priceOrderTable.cellValueFactory = PropertyValueFactory("price")

            wantedCountOrderTable.cellValueFactory = PropertyValueFactory("wantedCountOrder")
            wantedCountOrderTable.setOnEditCommit {
                try {
                    val value = it.newValue.toLong()

                    val currentGoods = dbController.goodsList[it.tablePosition.row]

                    if (value <= currentGoods.count) {
                        currentGoods.wantedCountOrder = value.toString()
                        currentGoods.total = value * dbController.goodsList[it.tablePosition.row].price

                    } else {
                        showWarning("Значение ($value) не должно превышать количество товара на складе (${currentGoods.count})")
                    }

                } catch (e: NumberFormatException) {
                    showWarning("\"${it.newValue}\" не является целым числом.")
                }
                orderTable.refresh()
            }
            wantedCountOrderTable.cellFactory = TextFieldTableCell.forTableColumn()

            totalOrderTable.cellValueFactory = PropertyValueFactory("total")

            orderTable.items = FXCollections.observableArrayList(dbController.goodsList)

//            styleRowColor()
        }

    }

    private fun styleRowColor() {
        val cellFactory: Callback<TableColumn<Goods, String>, TableCell<Goods, String>> =
            Callback<TableColumn<Goods, String>, TableCell<Goods, String>> {
                object : TextFieldTableCell<Goods, String>() {

                    override fun updateItem(item: String?, empty: Boolean) {
                        super.updateItem(item, empty)
                        if (empty) {
                            graphic = null
                            text = null
                        } else {
                            text = item.toString()
                            val row = tableRow
                            println(row.item.name)
//                            if (row.item.getColor().equals("red")) {
//                                row.styleClass.clear()
//                                row.styleClass.add("red-row")
//                            }
//                            if (row.item.getColor().equals("orange")) {
//                                row.styleClass.clear()
//                                row.styleClass.add("orange-row")
//                            }
//                            if (row.item.getColor().equals("green")) {
//                                row.styleClass.clear()
//                                row.styleClass.add("green-row")
//                            }
//                            if (row.item.getColor().equals("yellow")) {
//                                row.styleClass.clear()
//                                row.styleClass.add("yellow-row")
//                            }
                        }
                    }
                }
            }
//        wantedCountOrderTable.cellFactory = cellFactory
    }


    private fun initClientSelector() {
        clientSelector.items.addAll(dbController.getClientsNames())
        clientSelector.onAction = EventHandler {
            initOrderTable()

            val selectedClient = dbController.clientList.find { it.name == clientSelector.value }
            selectedClientInfo.text = """
                Текущий баланс клиента: ${selectedClient!!.currentBalance}
                Остаток по кредиту: ${selectedClient.remainingDebt}
            """.trimIndent()
        }
    }


    private fun initSellTypeSelector() {
        sellTypeSelector.items.addAll(sellTypes)
        sellTypeSelector.onAction = EventHandler {
            initOrderTable()
        }
    }

    private fun showWarning(text: String) {
        // TODO make sound

        val dialog = Dialog<String>()
        dialog.title = "Ошибка!"
        dialog.contentText = text
        dialog.dialogPane.buttonTypes.add(ButtonType("Ок", ButtonBar.ButtonData.OK_DONE))
        dialog.showAndWait()

    }


}
