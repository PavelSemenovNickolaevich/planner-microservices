package ru.javabegin.micro.planner.utils.rest.webclient

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import ru.javabegin.micro.planner.entity.User

@Component // спец. класс для вызова микросервисов пользователей с помощью WebClient
class UserWebClientBuilder {

    companion object {
        private const val baseUrlUser = "http://localhost:8765/planner-users/user/"
        private const val baseUrlData = "http://localhost:8765/planner-todo/data/"
    }

    // проверка - существует ли пользователь
    fun userExists(userId: Long): Boolean {
        try {
            val user: User = WebClient.create(baseUrlUser)
                .post()
                .uri("id")
                .bodyValue(userId)
                .retrieve()
                .bodyToFlux<User>(User::class.java)
                .blockFirst() // блокирует поток до получения 1й записи
            if (user != null) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    // проверка - существует ли пользователь
    fun userExistsAsync(userId: Long): Flux<User> {
        return WebClient.create(baseUrlUser)
            .post()
            .uri("id")
            .bodyValue(userId)
            .retrieve()
            .bodyToFlux<User>(User::class.java)
    }

    // иниц. начальных данных
    fun initUserData(userId: Long): Flux<Boolean> {
        return WebClient.create(baseUrlData)
            .post()
            .uri("init")
            .bodyValue(userId)
            .retrieve()
            .bodyToFlux<Boolean>(Boolean::class.java)
    }
}