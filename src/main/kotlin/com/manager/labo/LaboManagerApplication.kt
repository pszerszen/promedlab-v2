package com.manager.labo

import com.manager.labo.config.ViewConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties(ViewConfig::class)
class LaboManagerApplication

fun main(args: Array<String>) {
    val context = SpringApplicationBuilder(LaboManagerApplication::class.java).headless(false).run()
    val controller = context.getBean(Controller::class.java)
}
