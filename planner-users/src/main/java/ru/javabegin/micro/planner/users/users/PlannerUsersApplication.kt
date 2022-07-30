package ru.javabegin.micro.planner.users.users

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = ["ru.javabegin.micro.planner"])
@EnableJpaRepositories(basePackages = ["ru.javabegin.micro.planner.users.users"])
@RefreshScope
open class PlannerUsersApplication

    fun main(args: Array<String>) {
        runApplication<PlannerUsersApplication>(*args)
    }



//@SpringBootApplication
//@EnableEurekaClient
//@ComponentScan(basePackages = ["ru.javabegin.micro.planner"])
//@EnableJpaRepositories(basePackages = ["ru.javabegin.micro.planner.users"])
//@RefreshScope
//class PlannerUsersApplication
//
//fun main(args: Array<String>) {
//    runApplication<PlannerUsersApplication>(*args)
//}
