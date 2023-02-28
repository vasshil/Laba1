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
import javafx.scene.media.AudioClip
import javafx.util.Callback


class ViewController {

    private lateinit var dbController: DataBaseController

    private val buzzer = AudioClip(javaClass.getResource("/com/example/laba1/warning.mp3")!!.toExternalForm())


    // views


    // -----------------------client tab-----------------------

    @FXML
    private lateinit var searchClient: TextField

    @FXML
    private lateinit var clientTable: TableView<Client>
    @FXML
    private lateinit var nameClientTable: TableColumn<Client, String>
    @FXML
    private lateinit var totalPurchaseClientTable: TableColumn<Client, Long>
    @FXML
    private lateinit var currentBalanceClientTable: TableColumn<Client, Long>
    @FXML
    private lateinit var creditLimitClientTable: TableColumn<Client, Long>
    @FXML
    private lateinit var currentDebtClientTable: TableColumn<Client, Long>
    @FXML
    private lateinit var remainingDebtClientTable: TableColumn<Client, Long>
    @FXML
    private lateinit var commentaryClientTable: TableColumn<Client, String>

    @FXML
    private lateinit var selectedClientInfo: Label



    // -----------------------goods tab-----------------------

    @FXML
    private lateinit var goodsTable: TableView<Goods>
    @FXML
    private lateinit var nameGoodsTable: TableColumn<Goods, String>
    @FXML
    private lateinit var countGoodsTable: TableColumn<Goods, Int>
    @FXML
    private lateinit var priceGoodsTable: TableColumn<Goods, Int>

    @FXML
    private lateinit var newGoodsName: TextField
    @FXML
    private lateinit var newGoodsCount: TextField
    @FXML
    private lateinit var newGoodsPrice: TextField
    @FXML
    private lateinit var addGoods: Button



    // -----------------------order tab-----------------------

    @FXML
    private lateinit var clientSelector: ChoiceBox<String>
    @FXML
    private lateinit var sellTypeSelector: ChoiceBox<String>

    @FXML
    private lateinit var orderTable: TableView<Goods>
    @FXML
    private lateinit var nameOrderTable: TableColumn<Goods, String>
    @FXML
    private lateinit var countInStorageOrderTable: TableColumn<Goods, Int>
    @FXML
    private lateinit var priceOrderTable: TableColumn<Goods, Int>
    @FXML
    private lateinit var wantedCountOrderTable: TableColumn<Goods, String>
    @FXML
    private lateinit var totalOrderTable: TableColumn<Goods, Long>

    @FXML
    private lateinit var createOrder: Button
    @FXML
    private lateinit var totalOrderSum: Label


    private var totalOrderPrice = 0L


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

    @FXML
    private fun onNewGoods(event: ActionEvent) {

        val name: String = newGoodsName.text
        if (name.isEmpty()) {
            showWarning("Наименование товара не должно быть пустым.")
            return
        }

        val count: Int
        try {
            count = newGoodsCount.text.toInt()
        } catch (e: NumberFormatException) {
            showWarning("\"${newGoodsCount.text}\" не может быть количеством.")
            return
        }

        val price: Int
        try {
            price = newGoodsPrice.text.toInt()
        } catch (e: NumberFormatException) {
            showWarning("\"${newGoodsPrice.text}\" не может быть ценой.")
            return
        }

        val newGoods = Goods(0, name, count, price)
        dbController.addNewGoods(newGoods)

        goodsTable.items.add(newGoods)
        if (orderTable.items.isNotEmpty()) {
            orderTable.items = FXCollections.observableArrayList(dbController.orderList)
            updateTotalOrderSum()
//            orderTable.items.add(newGoods)
        }

        newGoodsName.clear()
        newGoodsCount.clear()
        newGoodsPrice.clear()



    }

    private fun initOrderTable() {

        if (clientSelector.value != null && sellTypeSelector.value != null) {

            dbController.getAllOrders()

            nameOrderTable.cellValueFactory = PropertyValueFactory("name")
            countInStorageOrderTable.cellValueFactory = PropertyValueFactory("count")
            priceOrderTable.cellValueFactory = PropertyValueFactory("price")

            wantedCountOrderTable.cellValueFactory = PropertyValueFactory("wantedCountOrder")
            wantedCountOrderTable.setOnEditCommit {

                try {
                    val value = it.newValue.toLong()

                    if (value < 0) {
                        showWarning("Количество товаров не должно быть отрицательным.")
                    } else {
                        val currentGoods = dbController.orderList[it.tablePosition.row]

                        if (value <= currentGoods.count) {
                            currentGoods.wantedCountOrder = value.toString()
                            currentGoods.total = value * currentGoods.price

                            updateTotalOrderSum()

                        } else {
                            showWarning("Значение ($value) не должно превышать количество товара на складе (${currentGoods.count})")
                        }

                    }

                } catch (e: NumberFormatException) {
                    showWarning("\"${it.newValue}\" не является целым числом.")
                }

                orderTable.refresh()

            }
            wantedCountOrderTable.cellFactory = TextFieldTableCell.forTableColumn()

            totalOrderTable.cellValueFactory = PropertyValueFactory("total")

            orderTable.items = FXCollections.observableArrayList(dbController.orderList)

            updateTotalOrderSum()

//            styleRowColor()
        }

    }

    private fun updateTotalOrderSum() {
        totalOrderPrice = 0L
        for (goods in dbController.orderList) {
            totalOrderPrice += goods.total
        }
        totalOrderSum.text = "(Сумма заказа: $totalOrderPrice$)"

    }

    private fun initClientSelector() {
        clientSelector.items.addAll(dbController.getClientsNames())
        clientSelector.onAction = EventHandler {
            initOrderTable()

            updateClientInfo()

        }
    }

    private fun updateClientInfo() {
        val selectedClient = dbController.clientList.find { it.name == clientSelector.value }
        selectedClientInfo.text = """
                Общий счет покупок: ${selectedClient!!.totalPurchase}
                Текущий баланс клиента: ${selectedClient.currentBalance}
                Остаток по кредиту: ${selectedClient.remainingDebt}
            """.trimIndent()
    }


    private fun initSellTypeSelector() {
        sellTypeSelector.items.addAll(sellTypes)
        sellTypeSelector.onAction = EventHandler {
            initOrderTable()
        }
    }

    @FXML
    fun onCreateOrder(event: ActionEvent) {
        if (clientSelector.value == null) {
            showWarning("Выберите клиента.")
            return
        }
        if (sellTypeSelector.value == null) {
            showWarning("Выберите тип продажи.")
            return
        }
        if (orderTable.items.isEmpty()) {
            showWarning("Добавьте товары для продажи.")
            return
        }

        val firstGoodsInOrder = dbController.orderList.find { it.wantedCountOrder.toInt() > 0 }
        if (firstGoodsInOrder == null) {
            showWarning("Не выбрано ни одного товара.")
            return
        }

        val selectedClient = dbController.clientList.find { it.name == clientSelector.value }

        when(sellTypeSelector.value) {
            sellTypes[0] -> { // Наличный расчет

                for (goods in dbController.goodsList) {
                    goods.count -= goods.wantedCountOrder.toInt()
                }

                selectedClient!!.totalPurchase += totalOrderPrice

                // TODO update db

            }
            sellTypes[1] -> { // Безналичный расчет

                if (totalOrderPrice > selectedClient!!.currentBalance) {
                    showWarning("На счете клиента недостаточно средств.")
                    return

                } else {

                    for (goods in dbController.goodsList) {
                        goods.count -= goods.wantedCountOrder.toInt()
                    }

                    selectedClient.totalPurchase += totalOrderPrice
                    selectedClient.currentBalance -= totalOrderPrice

                    // TODO update db


                }

            }
            sellTypes[2] -> { // Кредит

                if (totalOrderPrice > selectedClient!!.remainingDebt) { // заказ превышает лимит

                    if (totalOrderPrice > selectedClient.remainingDebt + selectedClient.currentBalance) { // превышавет лимит и баланс
                        showWarning("Стоимость заказа превышает кредитный лимит и баланс на счету.")
                        return

                    } else {

                        for (goods in dbController.goodsList) {
                            goods.count -= goods.wantedCountOrder.toInt()
                        }

                        selectedClient.totalPurchase += totalOrderPrice
                        selectedClient.currentDebt += (totalOrderPrice - selectedClient.currentBalance)
                        selectedClient.currentBalance = 0
                        selectedClient.countRemainingDebt()

                        // TODO update db

                    }

                } else {

                    for (goods in dbController.goodsList) {
                        goods.count -= goods.wantedCountOrder.toInt()
                    }

                    selectedClient.totalPurchase += totalOrderPrice
                    selectedClient.currentDebt += totalOrderPrice
                    selectedClient.countRemainingDebt()

                    // TODO update db


                }


            }
            sellTypes[3] -> { // Бартер

                val batteryGoods = dbController.goodsList.find { it.name == "Battery" }
                batteryGoods!!.count += totalOrderPrice.toInt()

                for (goods in dbController.goodsList) {
                    goods.count -= goods.wantedCountOrder.toInt()
                }

                // TODO update db


            }
            sellTypes[4] -> { // Взаимозачет

                for (goods in dbController.goodsList) {
                    goods.count += goods.wantedCountOrder.toInt()
                }

                if (totalOrderPrice > selectedClient!!.currentDebt) { // заказ превышает лимит

                    selectedClient.currentDebt = 0
                    selectedClient.countRemainingDebt()

                    // TODO update db

                } else {

                    selectedClient.currentDebt -= totalOrderPrice
                    selectedClient.countRemainingDebt()

                    // TODO update db


                }


            }
        }


        clientTable.refresh()
        goodsTable.refresh()
        orderTable.refresh()

        clearOrder()

        updateClientInfo()

        updateTotalOrderSum()

        goodsTable.items = FXCollections.observableArrayList(dbController.goodsList)


    }

    private fun clearOrder() {
        for (goods in dbController.orderList) {
            goods.wantedCountOrder = "0"
            goods.total = 0
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


    private fun showWarning(text: String) {
        buzzer.play()

        val dialog = Dialog<String>()
        dialog.title = "Ошибка!"
        dialog.contentText = text
        dialog.dialogPane.buttonTypes.add(ButtonType("Ок", ButtonBar.ButtonData.OK_DONE))
        dialog.showAndWait()

    }


}
