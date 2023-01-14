package com.manager.labo.utils

import java.lang.annotation.Inherited

@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@Target(AnnotationTarget.FIELD)
annotation class MappingField(val value: String = "")
