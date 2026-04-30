package com.example.snake_project

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val root = Pane()
        val scene = Scene(root, 600.0, 400.0)
        stage.title = "Snake"
        stage.scene = scene
        stage.show()
    }
}

  
