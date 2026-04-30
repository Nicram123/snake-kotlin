package com.example.snake_project
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.stage.Stage
import kotlin.random.Random

class Main : Application() {
    override fun start(stage: Stage) {
        val canvas = Canvas(Constants.WIDTH.toDouble(), Constants.HEIGHT.toDouble())
        val gc = canvas.graphicsContext2D
        val root = Group(canvas)
        val scene = Scene(root)
        stage.title = "Snake Kotlin"
        stage.scene = scene
        stage.show()
        val snake = Snake()
        val apple = Apple()
        // sterowanie strzałkami
        scene.setOnKeyPressed { event ->
            when (event.code) {
                javafx.scene.input.KeyCode.UP -> snake.direction = Direction.UP
                javafx.scene.input.KeyCode.DOWN -> snake.direction = Direction.DOWN
                javafx.scene.input.KeyCode.LEFT -> snake.direction = Direction.LEFT
                javafx.scene.input.KeyCode.RIGHT -> snake.direction = Direction.RIGHT
                else -> {}   // ← to usuwa błąd
            }

        }

        // start snake (2 segmenty)
        snake.cor.add(Segment(400, 400))
        snake.cor.add(Segment(440, 400))

        val timer = object : AnimationTimer() {

            var lastUpdate = 0L   // limiter prędkości

            override fun handle(now: Long) {

                // ruch co 150 ms (6–7 FPS)
                if (now - lastUpdate < 150_000_000) return
                lastUpdate = now

                // czyszczenie ekranu
                gc.fill = Constants.BLACK
                gc.fillRect(0.0, 0.0, Constants.WIDTH.toDouble(), Constants.HEIGHT.toDouble())
                // ruch węża (tylko move, bez update)
                snake.update()
                snake.move()      // 1. głowa idzie do przodu
                if (snake.bodyCollision()) {
                    stop()
                    println("Game Over")
                    stage.close()
                    return
                }
                snake.collision()

                // snake.update()
                // jabłko
                snake.ifPickedFruitRL(apple)

                // rysowanie jabłka
                drawSquare(gc, apple.x, apple.y, Constants.GREEN)

                // rysowanie węża
                for (seg in snake.cor) {
                    drawSquare(gc, seg.x, seg.y, Constants.RED)
                }

                // kolizja
                if (snake.bodyCollision() || snake.collision2()) {
                    stop()
                    println("Game Over")
                }
            }
        }

        timer.start()
    }

    fun drawSquare(gc: GraphicsContext, x: Int, y: Int, color: Color) {
        gc.fill = color
        gc.fillRect(
            x.toDouble(),
            y.toDouble(),
            Constants.SIZE.toDouble(),
            Constants.SIZE.toDouble()
        )
    }
}

fun main() {
    Application.launch(Main::class.java)
}
