package com.manager.labo.entities

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * @author Piotr
 */
@Entity
@Table(name = "examination")
class Examination : AbstractEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId", nullable = false)
    var patient: Patient? = null

    @Column(name = "date", columnDefinition = "DATETIME", nullable = false)
    var date: LocalDateTime? = null

    @Column(name = "code", nullable = false, columnDefinition = "varchar(1)")
    var code: String? = null

    @OneToMany(mappedBy = "examination", fetch = FetchType.LAZY)
    var examinationDetails: Set<ExaminationDetails> = HashSet()

}
