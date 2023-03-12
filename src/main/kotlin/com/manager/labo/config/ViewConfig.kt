package com.manager.labo.config

import com.manager.labo.view.DefaultSizeable
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("view")
data class ViewConfig(
    var defaultSizes: List<Size>
)

data class Size(
    var type: Class<out DefaultSizeable>,
    var width: Int,
    var height: Int
)
