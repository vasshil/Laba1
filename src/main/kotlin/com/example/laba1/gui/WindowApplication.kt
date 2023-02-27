package com.example.laba1.gui

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class WindowApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(WindowApplication::class.java.getResource("/com/example/laba1/view.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "Лаба 1"
        stage.scene = scene
        stage.show()
    }
}

