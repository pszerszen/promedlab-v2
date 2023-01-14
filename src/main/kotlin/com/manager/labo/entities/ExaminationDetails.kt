package com.manager.labo.entities

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * @author Piotr
 */
@Entity
@Table(name = "examination_details")
class ExaminationDetails : AbstractEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examinationId", nullable = false)
    var examination: Examination? = null

    @Column(name = "code", nullable = false, columnDefinition = "varchar(3)")
    var code: String? = null

    @Column(name = "value", nullable = true, columnDefinition = "int(11)")
    var value: Int? = null

    @Column(name = "staff_name", nullable = true, columnDefinition = "varchar(100)")
    var staffName: String? = null

    @Column(name = "date", columnDefinition = "DATETIME", nullable = false)
    var date: LocalDateTime? = null

}
