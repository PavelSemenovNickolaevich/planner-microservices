package ru.javabegin.micro.planner.users.users.mq.func

import lombok.Getter
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import reactor.core.publisher.Sinks
import java.sql.DriverManager.println

// работа с каналами
@Service
@Getter // помогает реализовать отправку сообщения с помощью функц. кода - по требованию (только после вызова соотв. метода)
class MessageFuncActions(  // каналы для обмена сообщениями
    private val messageFunc: MessageFunc
) {
    // отправка сообщения
    fun sendNewUserMessage(id: Long) {
        // добавляем в слушатель новое сообщение
        messageFunc.innerBus.emitNext(MessageBuilder.withPayload(id).build(), Sinks.EmitFailureHandler.FAIL_FAST)
        println("Message sent: $id")
    }
}