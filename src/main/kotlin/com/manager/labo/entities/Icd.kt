package com.manager.labo.entities

import jakarta.persistence.*
import org.hibernate.Hibernate

/**
 * @author Piotr
 */
@Entity
@Table(name = "icd")
data class Icd(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(name = "code1", columnDefinition = "varchar(1)", nullable = false)
    val code1: String,
    @Column(name = "name1", columnDefinition = "varchar(200)", nullable = false)
    val name1: String,
    @Column(name = "code2", columnDefinition = "varchar(3)", nullable = false)
    val code2: String,
    @Column(name = "name2", columnDefinition = "longtext", nullable = false)
    val name2: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Icd

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String = """
        Icd(code1='$code1', 
            name1='$name1', 
            code2='$code2', 
            name2='$name2')
        """.trimIndent()

}
