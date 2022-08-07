package ru.javabegin.micro.planner.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = ["ru.javabegin.micro.planner"])
@EnableJpaRepositories(basePackages = ["ru.javabegin.micro.planner.todo"])
@EnableFeignClients
@RefreshScope
class PlannerTodoApplication

    fun main(args: Array<String>) {
        runApplication<PlannerTodoApplication>(*args)
    }
