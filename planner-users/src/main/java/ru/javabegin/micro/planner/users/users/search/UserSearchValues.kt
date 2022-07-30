package ru.javabegin.micro.planner.users.users.search


// возможные значения, по которым можно искать задачи + значения сортировки
data class UserSearchValues(
    //здеь только обязательные параметры
    val pageNumber: Int,
    val pageSize: Int,
    val sortColumn: String,
    val sortDirection: String,
    val email: String
) {
    // поля поиска (все типы - объектные, не примитивные. Чтобы можно было передать null)
    val username: String? = null

    // постраничность


    // сортировка

}