package ru.javabegin.micro.planner.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

/*

справочноное значение - категория пользователя
может использовать для своих задач
содержит статистику по каждой категории

*/
@Entity
@Table(name = "category", schema = "todo", catalog = "planner_todo")
class Category : Serializable {
    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var id: Long? = null
    var title: String? = null

    @Column(
        name = "completed_count",
        updatable = false
    ) // т.к. это поле высчитывается автоматически в триггерах - вручную его не обновляем (updatable = false)
    var completedCount: Long? = null

    @Column(
        name = "uncompleted_count",
        updatable = false
    ) // т.к. это поле высчитывается автоматически в триггерах - вручную его не обновляем (updatable = false)
    var uncompletedCount: Long? = null

    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "user_id", referencedColumnName = "id") // по каким полям связаны эти 2 объекта (foreign key)
    //    private User user;
    @Column(name = "user_id")
    var userId: Long? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        var category = o as Category
        return id == category.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return title!!
    }
}