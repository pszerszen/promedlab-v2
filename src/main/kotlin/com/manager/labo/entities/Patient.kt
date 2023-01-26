package com.manager.labo.entities

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.LocalDate


@Entity
@Table(name = "patient")
data class Patient(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "firstname", columnDefinition = "varchar(100)", nullable = false)
    val firstName: String,
    @Column(name = "lastname", columnDefinition = "varchar(100)", nullable = false)
    val lastName: String,
    @Column(name = "pesel", columnDefinition = "varchar(11)", nullable = true, unique = true)
    val pesel: String? = null,
    @Column(name = "address1", columnDefinition = "varchar(100)", nullable = false)
    val address1: String,
    @Column(name = "address2", columnDefinition = "varchar(100)", nullable = true)
    val address2: String? = null,
    @Column(name = "city", columnDefinition = "varchar(100)", nullable = false)
    val city: String,
    @Column(name = "zipCode", columnDefinition = "varchar(20)", nullable = false)
    val zipCode: String,
    @Column(name = "phone", columnDefinition = "varchar(20)", nullable = false)
    val phone: String,
    @Column(name = "birth", columnDefinition = "DATE", nullable = false)
    val birth: LocalDate,
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    val examinations: Set<Examination> = mutableSetOf()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Patient

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String = """
        Patient(firstName='$firstName', 
                lastName='$lastName', 
                pesel=$pesel, 
                address1='$address1', 
                address2=$address2, 
                city='$city', 
                zipCode='$zipCode', 
                phone='$phone', 
                birth=$birth, 
                examinations=$examinations)
    """.trimIndent()

}
