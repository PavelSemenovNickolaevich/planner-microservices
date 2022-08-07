package ru.javabegin.micro.planner.entity

import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter
import org.hibernate.annotations.Type
import java.io.Serializable
import java.util.*
import javax.persistence.*

/*

задачи пользователя

*/
@Entity
@Table(name = "task", schema = "todo", catalog = "planner_user")
class Task : Serializable {
    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
     var id: Long? = null
     var title: String? = null

    @Type(type = "org.hibernate.type.NumericBooleanType") // для автоматической конвертации числа в true/false
     var completed // 1 = true, 0 = false
            : Boolean? = null

    @Column(name = "task_date") // в БД поле называется task_date, т.к. нельзя использовать системное имя date
     var taskDate: Date? = null

    // задача может иметь только один приоритет (с обратной стороны - один и тот же приоритет может быть использоваться в множестве задач)
    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
     var priority: Priority? = null

    // задача может иметь только одну категорию (с обратной стороны - одна и та же категория может быть использоваться в множестве задач)
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
     var category: Category? = null

    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "user_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    //    private User user; // для какого пользователя задача
    @Column(name = "user_id")
     var userId: Long? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val task = o as Task
        return id == task.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return title!!
    }
}