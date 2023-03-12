package com.manager.labo.config

import com.manager.labo.view.DefaultSizeable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.awt.Dimension

@Configuration
class ContextConfiguration {

    @Bean
    fun defaultSizeProvider(viewConfig: ViewConfig): (Class<out DefaultSizeable>) -> Dimension {
        return { type ->
            viewConfig.defaultSizes
                .find { it.type.isAssignableFrom(type) }
                .let { toDimension(it!!) }
        }
    }

    private fun toDimension(size: Size): Dimension {
        return Dimension(size.width, size.height)
    }
}
