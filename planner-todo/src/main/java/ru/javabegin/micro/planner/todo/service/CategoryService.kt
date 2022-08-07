package ru.javabegin.micro.planner.todo.service

import org.springframework.stereotype.Service
import ru.javabegin.micro.planner.entity.Category
import ru.javabegin.micro.planner.todo.repo.CategoryRepository
import javax.transaction.Transactional

@Service
@Transactional
class CategoryService(private val repository: CategoryRepository) {
    fun findById(id: Long): Category {
        return repository.findById(id).get()
    }

    fun findAll(userId: Long): List<Category> {
        return repository.findByUserIdOrderByTitleAsc(userId)
    }

    fun add(category: Category): Category {
        return repository.save(category)
    }

    fun update(category: Category): Category {
        return repository.save(category)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    fun findByTitle(title: String?, userId: Long): List<Category> {
        return repository.findByTitle(title, userId)
    }
}