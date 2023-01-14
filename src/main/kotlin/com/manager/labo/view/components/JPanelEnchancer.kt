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

class JPanelEnchancer(panel: JPanel) {
    private val panel: JPanel

    init {
        this.panel = panel
    }

    fun addAction(actionCommand: String, actionListener: ActionListener?): JPanelEnchancer {
        try {
            for (field in panel.javaClass.declaredFields) {
                val actionCommandAnnotation: ActionCommand = field.getAnnotation(ActionCommand::class.java)
                if (actionCommand == actionCommandAnnotation.value) {
                    field.isAccessible = true
                    when (val obj = field[panel]) {
                        is JButton -> obj.addActionListener(actionListener)
                        is JComboBox<*> -> obj.addActionListener(actionListener)
                        else -> log.error("Cannot add ActionListener to field {} of type {}", field.name, obj.javaClass.canonicalName)
                    }
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
            .forEach { try {
                it.isAccessible = true
                val button: JButton = it[panel] as JButton
                button.actionCommand = it.getAnnotation(ActionCommand::class.java).value
            } catch (e: Exception) {
                log.error("Error...", e)
            } }
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
            it.font = helveticaPlain14
            it.isFocusable = false
        }
        standardActionsForComponent(JLabel::class.java) { it.font = helveticaPlain14 }
        initButtonsActionCommands()
        return this
    }

    fun <C : JComponent?> standardActionsForComponent(type: Class<C>?, action: Consumer<C>): JPanelEnchancer {
        Stream.of(panel.javaClass.superclass.declaredFields, panel.javaClass.declaredFields)
            .flatMap { Arrays.stream(it) }
            .filter { it.type == type }
            .forEach {
                try {
                    it.isAccessible = true
                    action.accept(it.get(panel) as C)
                } catch (e: Exception) {
                    log.error("Error...", e)
                }
            }
        return this
    }

    companion object {
        private val log = LoggerFactory.getLogger(JPanelEnchancer::class.java)
        private val helveticaPlain14 = Font("Helvetica", Font.PLAIN, 14)
    }
}
