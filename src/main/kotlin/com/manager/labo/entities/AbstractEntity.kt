package com.manager.labo.entities

import jakarta.persistence.*

/**
 * A basic entity type containing an id field. Every entity should extend this type.
 *
 * @author Piotr
 */
@MappedSuperclass
abstract class AbstractEntity (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
)
