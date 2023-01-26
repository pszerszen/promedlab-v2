package com.manager.labo.entities

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.LocalDateTime

/**
 * @author Piotr
 */
@Entity
@Table(name = "examination_details")
data class ExaminationDetails(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examinationId", nullable = false)
    val examination: Examination,
    @Column(name = "code", nullable = false, columnDefinition = "varchar(3)")
    val code: String,
    @Column(name = "value", nullable = true, columnDefinition = "int(11)")
    val value: Int? = null,
    @Column(name = "staff_name", nullable = true, columnDefinition = "varchar(100)")
    val staffName: String? = null,
    @Column(name = "date", columnDefinition = "DATETIME", nullable = false)
    val date: LocalDateTime
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ExaminationDetails

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
    override fun toString(): String = """
        ExaminationDetails(examination=$examination, 
                           code='$code', 
                           value=$value, 
                           staffName=$staffName, 
                           date=$date)
    """.trimIndent()

}
