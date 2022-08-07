package ru.javabegin.micro.planner.todo.mq.func

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import ru.javabegin.micro.planner.todo.service.TestDataService
import java.util.function.Consumer

@Configuration // spring считывает бины и создает соотв. каналы
// описываются все каналы с помощью функциональных методов
// этот способ - рекомендуемый, вместо старого способа (@Binding, интерфейсы)
class MessageFunc(  // для заполнения тестовых данных
    private val testDataService: TestDataService
) {
    // получает id пользователя и запускает создание тестовых данных
    // название метода должно совпадать с настройками definition и bindings в файлах properties (или yml)
    @Bean
    fun newUserActionConsume(): Consumer<Message<Long?>> {
        return Consumer { message: Message<Long?> -> testDataService.initTestData(message.payload) }
    }
}