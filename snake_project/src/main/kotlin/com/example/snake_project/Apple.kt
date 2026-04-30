package com.example.snake_project

import kotlin.random.Random
import com.example.snake_project.Constants.WIDTH
import com.example.snake_project.Constants.SIZE2

class Apple {
    var len: Int = WIDTH / SIZE2
    var x: Int = Random.nextInt(0, len) * SIZE2
    var y: Int = Random.nextInt(0, len) * SIZE2

    fun generateNew() {
        x = Random.nextInt(0, len) * SIZE2
        y = Random.nextInt(0, len) * SIZE2
    }
}
