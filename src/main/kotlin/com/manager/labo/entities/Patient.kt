package com.manager.labo.entities

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "patient")
class Patient : AbstractEntity() {
    @Column(name = "firstname", columnDefinition = "varchar(100)", nullable = false)
    var firstName: String? = null

    @Column(name = "lastname", columnDefinition = "varchar(100)", nullable = false)
    var lastName: String? = null

    @Column(name = "pesel", columnDefinition = "varchar(11)", nullable = true, unique = true)
    var pesel: String? = null

    @Column(name = "address1", columnDefinition = "varchar(100)", nullable = false)
    var address1: String? = null

    @Column(name = "address2", columnDefinition = "varchar(100)", nullable = true)
    var address2: String? = null

    @Column(name = "city", columnDefinition = "varchar(100)", nullable = false)
    var city: String? = null

    @Column(name = "zipCode", columnDefinition = "varchar(20)", nullable = false)
    var zipCode: String? = null

    @Column(name = "phone", columnDefinition = "varchar(20)", nullable = false)
    var phone: String? = null

    @Column(name = "birth", columnDefinition = "DATE", nullable = false)
    var birth: LocalDate? = null

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    var examinations: Set<Examination> = HashSet()

}
