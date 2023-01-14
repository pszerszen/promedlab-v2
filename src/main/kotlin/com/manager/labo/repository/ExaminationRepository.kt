package com.manager.labo.repository

import com.manager.labo.entities.Examination
import org.springframework.data.jpa.repository.JpaRepository

interface ExaminationRepository: JpaRepository<Examination, Long> {
}
