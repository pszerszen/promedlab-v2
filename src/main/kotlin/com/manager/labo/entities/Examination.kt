package com.manager.labo.entities

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.LocalDateTime

/**
 * @author Piotr
 */
@Entity
@Table(name = "examination")
data class Examination(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId", nullable = false)
    val patient: Patient,
    @Column(name = "date", columnDefinition = "DATETIME", nullable = false)
    val date: LocalDateTime,
    @Column(name = "code", nullable = false, columnDefinition = "varchar(1)")
    val code: String,
    @OneToMany(mappedBy = "examination", fetch = FetchType.LAZY)
    val examinationDetails: MutableSet<ExaminationDetails> = mutableSetOf()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Examination

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String = """
        Examination(patient=$patient, 
                    date=$date, 
                    code='$code', 
                    examinationDetails=$examinationDetails)
    """.trimIndent()

}
