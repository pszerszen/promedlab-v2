package com.manager.labo.service

import com.manager.labo.entities.Icd
import com.manager.labo.repository.IcdRepository
import io.kotest.matchers.collections.shouldBeSortedWith
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class IcdServiceTest {

    @MockK
    lateinit var icdRepository: IcdRepository

    @InjectMockKs
    lateinit var tested: IcdService

    @Test
    fun getByCode2(@MockK icd: Icd) {
        val code2 = "code2"
        every { icdRepository.getByCode2(code2) } returns icd

        tested.getByCode2(code2) shouldBe icd
    }

    @Test
    fun getGroups(@MockK icd1: Icd, @MockK icd2: Icd) {
        every { icdRepository.findAll() } returns listOf(icd1, icd1, icd2)
        every { icd1.code1 } returns "code1"
        every { icd1.name1 } returns "name1"
        every { icd2.code1 } returns "code2"
        every { icd2.name1 } returns "name2"

        val actual = tested.groups
        actual shouldHaveSize 2
        actual shouldContain "code1 - name1"
        actual shouldContain "code2 - name2"
        actual shouldBeSortedWith String::compareTo
    }

    @Test
    fun getExaminationsFromGroup(@MockK icd: Icd) {
        every { icdRepository.getByCode1("code1") } returns listOf(icd)
        every { icd.code2 } returns "code2"
        every { icd.name2 } returns "name2"

        val actual = tested.getExaminationsFromGroup("code1")
        actual shouldHaveSize 1
        actual shouldContain "code2 - name2"
    }
}
