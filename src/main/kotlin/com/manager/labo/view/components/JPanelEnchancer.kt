package com.manager.labo.view.components

import com.manager.labo.utils.ActionCommand
import org.slf4j.LoggerFactory
import java.awt.Font
import java.awt.event.ActionListener
import java.awt.event.KeyListener
import java.util.*
import java.util.function.Consumer
import java.util.stream.Stream
import javax.swing.*

class JPanelEnchancer(private val panel: JPanel) {

    fun addAction(actionCommand: String, actionListener: ActionListener?): JPanelEnchancer {
        try {
            panel.javaClass.declaredFields
                .filter { it.isAnnotationPresent(ActionCommand::class.java) }
                .filter { it.getAnnotation(ActionCommand::class.java).value == actionCommand }
                .forEach {
                    it.isAccessible = true
                    when (val obj = it[panel]) {
                        is JButton -> obj.addActionListener(actionListener)
                        is JComboBox<*> -> obj.addActionListener(actionListener)
                        else -> log.error("Cannot add ActionListener to field {} of type {}", it.name, obj.javaClass.canonicalName)
                    }
                }
        } catch (e: IllegalArgumentException) {
            log.error("Error while attaching action:", e)
        } catch (e: IllegalAccessException) {
            log.error("Error while attaching action:", e)
        }
        return this
    }

    fun initButtonsActionCommands(): JPanelEnchancer {
        panel.javaClass.declaredFields
            .filter { it.type == JButton::class.java && it.isAnnotationPresent(ActionCommand::class.java) }
            .forEach {
                try {
                    it.isAccessible = true
                    val button: JButton = it[panel] as JButton
                    button.actionCommand = it.getAnnotation(ActionCommand::class.java).value
                } catch (e: Exception) {
                    log.error("Error...", e)
                }
            }
        return this
    }

    fun addListeners(actionListener: ActionListener?, keyListener: KeyListener?): JPanelEnchancer {
        standardActionsForComponent(JButton::class.java) {
            it.addActionListener(actionListener)
            it.addKeyListener(keyListener)
        }
        return this
    }

    fun standardActions(): JPanelEnchancer {
        standardActionsForComponent(JButton::class.java) {
            it.font = arialPlain14
            it.isFocusable = false
        }
        standardActionsForComponent(JLabel::class.java) { it.font = arialPlain14 }
        initButtonsActionCommands()
        return this
    }

    private fun <C : JComponent?> standardActionsForComponent(type: Class<C>, action: Consumer<C>): JPanelEnchancer {
        Stream.of(panel.javaClass.superclass.declaredFields, panel.javaClass.declaredFields)
            .flatMap { Arrays.stream(it) }
            .filter { it.type == type }
            .peek { it.isAccessible = true }
            .map { it.get(panel) }
            .map { type.cast(it) }
            .forEach { action.accept(it) }
        return this
    }

    companion object {
        private val log = LoggerFactory.getLogger(JPanelEnchancer::class.java)
        private val arialPlain14 = Font("Arial", Font.PLAIN, 14)
    }
}
