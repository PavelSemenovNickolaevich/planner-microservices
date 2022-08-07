package ru.javabegin.micro.planner.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*

/*

Все доступные роли, которые будут привязаны к пользователю

*/
@Entity
@Table(name = "role_data", schema = "users", catalog = "planner_user")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name // название роли
            : String? = null

    @JvmField
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var users: Set<User>? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val role = o as Role
        return id == role.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return name!!
    }
}