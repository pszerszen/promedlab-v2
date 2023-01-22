package com.manager.labo.repository

import com.manager.labo.entities.Patient
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository: JpaRepository<Patient, Long> {

    fun getByPesel(pesel: String): Patient
}
