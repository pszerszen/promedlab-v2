package com.manager.labo.service

import com.manager.labo.entities.Icd
import com.manager.labo.repository.IcdRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Piotr
 */
@Service
@Transactional
class IcdService(private val icdRepository: IcdRepository) {

    fun getByCode2(code: String): Icd = icdRepository.getByCode2(code)

    val groups: List<String>
        get() = allIcds
            .map { it.code1 + " - " + it.name1 }
            .distinct()
            .sorted()

    fun getExaminationsFromGroup(code: String): List<String> = getByCode1(code)
        .map { it.code2 + " - " + it.name2 }

    private fun getByCode1(code: String): List<Icd> = icdRepository.getByCode1(code)

    private val allIcds: List<Icd> get() = icdRepository.findAll()
}
