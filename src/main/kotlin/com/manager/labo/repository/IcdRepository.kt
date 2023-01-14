package com.manager.labo.repository

import com.manager.labo.entities.Icd
import org.springframework.data.jpa.repository.JpaRepository

interface IcdRepository: JpaRepository<Icd, Long> {

    fun getByCode1(code: String): List<Icd>
    fun getByCode2(code: String): Icd
}
