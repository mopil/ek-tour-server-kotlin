package com.ektour.repository

import com.ektour.entity.Admin
import com.ektour.entity.Estimate
import com.ektour.entity.Visit
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface VisitRepository : JpaRepository<Visit, Long> {}

interface AdminRepository : JpaRepository<Admin, Long> {
    fun existsAdminByPassword(password: String): Boolean
}

interface EstimateRepository : JpaRepository<Estimate, Long> {

    @Query("SELECT e FROM Estimate e WHERE e.visibility = true")
    fun findAllByPage(pageable: Pageable): Page<Estimate>

    @Query("SELECT e FROM Estimate e")
    fun findAllByAdmin(pageable: Pageable): Page<Estimate>

    @Query("SELECT COUNT(e) FROM Estimate e WHERE e.visibility = true")
    fun countAll(): Int

    fun findAllByPhoneAndPassword(pageable: Pageable, phone: String, password: String): Page<Estimate>

    fun findAllByPhoneAndPassword(phone: String, password: String): List<Estimate>

    fun findByIdAndPhoneAndPassword(id: Long, phone: String, password: String): Estimate?

    @Query("SELECT e FROM Estimate e WHERE e.createdDate BETWEEN :start AND :end AND e.name LIKE %:name%")
    fun searchAllByName(
        pageable: Pageable,
        @Param("start") start: String,
        @Param("end") end: String,
        @Param("name") name: String
    ): Page<Estimate>

    @Query("SELECT e FROM Estimate e WHERE e.createdDate BETWEEN :start AND :end AND e.phone LIKE %:phone%")
    fun searchAllByPhone(
        pageable: Pageable,
        @Param("start") start: String,
        @Param("end") end: String,
        @Param("phone") phone: String
    ): Page<Estimate>

    @Query("SELECT e FROM Estimate e WHERE e.createdDate BETWEEN :start AND :end")
    fun searchAllByDate(
        pageable: Pageable,
        @Param("start") start: String,
        @Param("end") end: String
    ): Page<Estimate>
}