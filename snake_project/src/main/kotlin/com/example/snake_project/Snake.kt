package com.example.snake_project
import com.example.snake_project.Constants.WIDTH
import com.example.snake_project.Constants.HEIGHT
import com.example.snake_project.Constants.SIZE
import com.example.snake_project.Constants.SIZE2
import kotlin.random.Random
class Snake {
    var cor: MutableList<Segment> = mutableListOf()
    var count: Int = 1
    var a: Int = 0
    var b: Int = 0
    var prevKey: String? = null
    var direction = Direction.RIGHT

    fun move() {
        when (direction) {
            Direction.UP -> cor[0].y -= SIZE
            Direction.DOWN -> cor[0].y += SIZE
            Direction.LEFT -> cor[0].x -= SIZE
            Direction.RIGHT -> cor[0].x += SIZE
        }
    }

    fun ifPickedFruitRL(apple: Apple): Boolean {
        if (apple.x == cor[0].x && apple.y == cor[0].y) {
            buildSnake()
            apple.generateNew()
            return true
        }
        return false
    }

    fun buildSnake() {
        if (cor.size < 1) return

        val last = cor.last()
        val beforeLast = if (cor.size > 1) cor[cor.size - 2] else last

        val dx = last.x - beforeLast.x
        val dy = last.y - beforeLast.y

        // dodaj segment w kierunku przeciwnym do ruchu ogona
        val newX = last.x + dx
        val newY = last.y + dy

        cor.add(Segment(newX, newY))
    }

    fun update() {
        if (cor.size < 2) return

        // zaczynamy od końca ogona
        for (i in cor.size - 1 downTo 1) {
            cor[i].x = cor[i - 1].x
            cor[i].y = cor[i - 1].y
        }
    }


    fun bodyCollision(): Boolean {
        if (cor.size <= 2) return false

        val head = cor[0]
        for (segment in cor.drop(1)) {
            if (head.x == segment.x && head.y == segment.y) return true
        }
        return false
    }

    fun collision2(): Boolean {
        val head = cor[0]
        return head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT
    }

    fun collision() {
        val head = cor[0]

        if (head.x < 0) head.x = WIDTH - SIZE
        else if (head.x >= WIDTH) head.x = 0
        else if (head.y < 0) head.y = HEIGHT - SIZE
        else if (head.y >= HEIGHT) head.y = 0
    }

    fun ifTurnBackOpposite(action: Int): Boolean {
        return when (action) {
            0 -> prevKey != "down"
            1 -> prevKey != "up"
            2 -> prevKey != "right"
            3 -> prevKey != "left"
            else -> true
        }
    }

    fun moveRL(action: Int) {
        if (!ifTurnBackOpposite(action)) return

        when (action) {
            0 -> { a = 0; b = -SIZE; prevKey = "up" }
            1 -> { a = 0; b = SIZE; prevKey = "down" }
            2 -> { a = -SIZE; b = 0; prevKey = "left" }
            3 -> { a = SIZE; b = 0; prevKey = "right" }
        }
    }

    fun futureCollision(): List<Int> {
        val dangerousMove = MutableList(4) { 0 }

        for (action in 0..3) {
            val temp = this.copy()

            if (!temp.ifTurnBackOpposite(action)) {
                dangerousMove[action] = 1
                continue
            }

            temp.moveRL(action)
            temp.update()

            if (temp.bodyCollision() || temp.collision2()) {
                dangerousMove[action] = 1
            }
        }

        return dangerousMove
    }

    fun copy(): Snake {
        val newSnake = Snake()
        newSnake.cor = this.cor.map { Segment(it.x, it.y) }.toMutableList()
        newSnake.a = this.a
        newSnake.b = this.b
        newSnake.prevKey = this.prevKey
        return newSnake
    }


}
