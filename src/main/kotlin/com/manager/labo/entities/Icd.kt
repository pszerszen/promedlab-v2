package com.manager.labo.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * @author Piotr
 */
@Entity
@Table(name = "icd")
class Icd : AbstractEntity() {
    @Column(name = "code1", columnDefinition = "varchar(1)", nullable = false)
    var code1: String? = null

    @Column(name = "name1", columnDefinition = "varchar(200)", nullable = false)
    var name1: String? = null

    @Column(name = "code2", columnDefinition = "varchar(3)", nullable = false)
    var code2: String? = null

    @Column(name = "name2", columnDefinition = "longtext", nullable = false)
    var name2: String? = null

}
