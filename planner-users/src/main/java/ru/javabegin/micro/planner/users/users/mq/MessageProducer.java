package ru.javabegin.micro.planner.users.users.mq;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(TodoBinding.class)
// связываем класс, чтобы он имел возможность исп. каналы, который описаны в интерфейсе
public class MessageProducer {

    private TodoBinding todoBinding; // содержит все описанные каналы

    public MessageProducer(TodoBinding todoBinding) {
        this.todoBinding = todoBinding;
    }


    // отправка сообщения при создании нового пользователя
    public void initUserData(Long userId) {

        // контейнер для добавления данных и headers
        Message message = MessageBuilder.withPayload(userId).build();

        todoBinding.todoOutputChannel().send(message); // выбираем канал и отправляем сообщение
    }
}
