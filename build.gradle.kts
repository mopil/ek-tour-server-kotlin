import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
	id("org.jlleitschuh.gradle.ktlint-idea") version "10.0.0"
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

noArg {
	annotation("javax.persistence.Entity")
}

group = "com"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("mysql:mysql-connector-java")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.apache.poi:poi:5.0.0")
	implementation("org.apache.poi:poi-ooxml:5.0.0")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.swagger:swagger-annotations:1.5.20")

	testImplementation(platform("org.junit:junit-bom:5.9.1"))
	testImplementation("org.junit.jupiter:junit-jupiter")

	implementation("io.springfox:springfox-swagger-ui")
	implementation("io.springfox:springfox-boot-starter")
	implementation("com.github.maricn:logback-slack-appender:1.6.1")

	implementation("com.google.code.gson:gson:2.9.0")
	implementation("org.springframework.mobile:spring-mobile-device:1.1.5.RELEASE")

	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

	testImplementation("io.kotest:kotest-assertions-core-jvm:5.4.2")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.4.2")
	testImplementation("io.kotest:kotest-property:5.4.2")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.1")
	testImplementation("com.ninja-squad:springmockk:2.0.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
