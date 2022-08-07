package ru.javabegin.micro.planner.todo.controller

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.javabegin.micro.planner.entity.Priority
import ru.javabegin.micro.planner.todo.search.PrioritySearchValues
import ru.javabegin.micro.planner.todo.service.PriorityService
import ru.javabegin.micro.planner.utils.rest.resttemplate.UserRestBuilder

/*

Чтобы дать меньше шансов для взлома (например, CSRF атак): POST/PUT запросы могут изменять/фильтровать закрытые данные, а GET запросы - для получения незащищенных данных
Т.е. GET-запросы не должны использоваться для изменения/получения секретных данных

Если возникнет exception - вернется код  500 Internal Server Error, поэтому не нужно все действия оборачивать в try-catch

Используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON,
иначе пришлось бы добавлять лишние объекты в код, использовать @ResponseBody для ответа, указывать тип отправки JSON

Названия методов могут быть любыми, главное не дублировать их имена и URL mapping

*/
@RestController
@RequestMapping("/priority") // базовый URI
class PriorityController     // используем автоматическое внедрение экземпляра класса через конструктор
// не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    (// доступ к данным из БД
    private val priorityService: PriorityService, // микросервисы для работы с пользователями
    private val userRestBuilder: UserRestBuilder
) {
    @PostMapping("/all")
    fun findAll(@RequestBody userId: Long?): List<Priority> {
        return priorityService.findAll(userId!!)
    }

    @PostMapping("/add")
    fun add(@RequestBody priority: Priority): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (priority.id != null && priority.id != 0L) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return ResponseEntity<Any>("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение title
        if (priority.title == null || priority.title!!.trim { it <= ' ' }.length == 0) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение color
        if (priority.color == null || priority.color!!.trim { it <= ' ' }.length == 0) {
            return ResponseEntity<Any>("missed param: color", HttpStatus.NOT_ACCEPTABLE)
        }

        // если такой пользователь существует
        return if (userRestBuilder.userExists(priority.userId!!)) { // вызываем микросервис из другого модуля
            ResponseEntity.ok(
                priorityService.add(
                    priority
                )
            ) // возвращаем добавленный объект с заполненным ID
        } else ResponseEntity<Any>(
            "user id=" + priority.userId + " not found",
            HttpStatus.NOT_ACCEPTABLE
        )

        // если пользователя НЕ существует
    }

    @PutMapping("/update")
    fun update(@RequestBody priority: Priority): ResponseEntity<*> {


        // проверка на обязательные параметры
        if (priority.id == null || priority.id == 0L) {
            return ResponseEntity<Any?>("missed param: id", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение title
        if (priority.title == null || priority.title!!.trim { it <= ' ' }.length == 0) {
            return ResponseEntity<Any?>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение color
        if (priority.color == null || priority.color!!.trim { it <= ' ' }.length == 0) {
            return ResponseEntity<Any?>("missed param: color", HttpStatus.NOT_ACCEPTABLE)
        }

        // save работает как на добавление, так и на обновление
        priorityService.update(priority)
        return ResponseEntity<Any?>(HttpStatus.OK) // просто отправляем статус 200 (операция прошла успешно)
    }

    // параметр id передаются не в BODY запроса, а в самом URL
    @PostMapping("/id")
    fun findById(@RequestBody id: Long): ResponseEntity<Any> {
        var priority: Priority? = null

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        priority = try {
            priorityService.findById(id)
        } catch (e: NoSuchElementException) { // если объект не будет найден
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }
        return ResponseEntity.ok(priority)
    }

    // для удаления используем типа запроса put, а не delete, т.к. он позволяет передавать значение в body, а не в адресной строке
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<*> {

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            priorityService.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            e.printStackTrace()
            return ResponseEntity<Any?>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }
        return ResponseEntity<Any?>(HttpStatus.OK) // просто отправляем статус 200 (операция прошла успешно)
    }

    // поиск по любым параметрам PrioritySearchValues
    @PostMapping("/search")
    fun search(@RequestBody prioritySearchValues: PrioritySearchValues): ResponseEntity<Any> {

        // проверка на обязательные параметры
        return if (prioritySearchValues.userId == 0L) {
            ResponseEntity<Any>("missed param: user id", HttpStatus.NOT_ACCEPTABLE)
        } else ResponseEntity.ok(
            priorityService.find(prioritySearchValues.title, prioritySearchValues.userId)
        )

        // если вместо текста будет пусто или null - вернутся все категории
    }
}