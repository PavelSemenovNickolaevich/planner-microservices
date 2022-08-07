package ru.javabegin.micro.planner.todo.search

import java.util.*

class SearchValues

//в этом классе все дата файлы для поиска
data class TaskSearchValues(
    var sortColumn: String,
    var sortDirection: String,
    var pageSize: Int,
    var pageNumber: Int,
    var userId: Long
) {
    // поля поиска (все типы - объектные, не примитивные. Чтобы можно было передать null)
    var title: String? = null
    var completed: Int? = null
    var priorityId: Long? = null
    var categoryId: Long? = null
    var dateFrom // для задания периода по датам
            : Date? = null
    var dateTo: Date? = null
}


// возможные значения, по которым можно искать приоритеты
data class PrioritySearchValues(
    var userId: Long
) {
    var title // такое же название должно быть у объекта на frontend - необязательно заполнять
            : String? = null
}

data class CategorySearchValues(
    var userId: Long
) {
    var title: String? = null
}