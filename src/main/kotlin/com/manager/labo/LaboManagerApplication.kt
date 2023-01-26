package com.manager.labo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class LaboManagerApplication

fun main(args: Array<String>) {
    val context = SpringApplicationBuilder(LaboManagerApplication::class.java).headless(false).run()
    val controller = context.getBean(Controller::class.java)
//	runApplication<LaboManagerApplication>(*args)
}
