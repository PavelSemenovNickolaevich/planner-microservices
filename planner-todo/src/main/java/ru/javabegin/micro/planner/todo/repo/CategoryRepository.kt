package ru.javabegin.micro.planner.todo.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.javabegin.micro.planner.entity.Category

//ооп: абстракция - реализация - здесь описываем все доступные доступы к данным
@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    // поиск категорий пользователя (по названию)
    fun findByUserIdOrderByTitleAsc(id: Long): List<Category>

    // поиск значений по названию для конкретного пользователя
    @Query(
        "SELECT c FROM Category c where " +
                "(:title is null or :title='' " +  // если передадим параметр title пустым, то выберутся все записи (сработает именно это условие)
                " or lower(c.title) like lower(concat('%', :title,'%'))) " +  // если параметр title не пустой, то выполнится уже это условие
                " and c.userId=:id  " +  // фильтрация для конкретного пользователя
                " order by c.title asc"
    )
    fun  // сортировка по названию
            findByTitle(@Param("title") title: String?, @Param("id") id: Long): List<Category>
}