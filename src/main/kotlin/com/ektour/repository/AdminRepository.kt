package com.ektour.repository

import com.ektour.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
    fun existsAdminByPassword(password: String): Boolean
}
