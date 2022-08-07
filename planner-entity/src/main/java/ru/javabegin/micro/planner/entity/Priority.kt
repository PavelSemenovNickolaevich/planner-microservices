package ru.javabegin.micro.planner.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

/*

справочноное значение - приоритет пользователя
может использовать для своих задач

*/
@Entity
@Table(name = "priority", schema = "todo", catalog = "planner_todo")
class Priority : Serializable {
    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var id: Long? = null
    var title: String? = null
    var color: String? = null

    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "user_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    //    private User user;
    @Column(name = "user_id")
    var userId: Long? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val priority = o as Priority
        return id == priority.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return title!!
    }
}