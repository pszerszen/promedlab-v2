package com.manager.labo.utils

import com.manager.labo.view.components.TableModel
import java.lang.annotation.Inherited

@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@Target(AnnotationTarget.FIELD)
annotation class DisplayInJTable(val name: TableModel, val order: Int)
