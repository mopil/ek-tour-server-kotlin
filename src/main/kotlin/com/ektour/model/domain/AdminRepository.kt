package com.ektour.model.domain

import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
    fun existsAdminByPassword(password: String): Boolean
}
