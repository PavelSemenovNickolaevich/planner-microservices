package ru.javabegin.micro.planner

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.jvm.JvmStatic

@SpringBootApplication
class PlannerEntityApplication

    fun main(args: Array<String>) {
        runApplication<PlannerEntityApplication>(*args)
    }
