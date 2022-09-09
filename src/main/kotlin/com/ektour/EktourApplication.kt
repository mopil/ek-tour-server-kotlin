package com.ektour

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableAsync
class EktourApplication

fun main(args: Array<String>) {
	runApplication<EktourApplication>(*args)
}
