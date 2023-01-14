package com.manager.labo.repository

import com.manager.labo.entities.ExaminationDetails
import org.springframework.data.jpa.repository.JpaRepository

interface ExaminationDetailsRepository: JpaRepository<ExaminationDetails, Long> {
}
