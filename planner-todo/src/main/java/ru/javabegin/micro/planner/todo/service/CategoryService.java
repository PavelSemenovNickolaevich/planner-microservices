package ru.javabegin.micro.planner.todo.service;


import org.springframework.stereotype.Service;
import ru.javabegin.micro.planner.entity.Category;
import ru.javabegin.micro.planner.todo.repo.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service


@Transactional
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category findById(long id) {
        return repository.findById(id).get();
    }

    public List<Category> findAll(Long userId) {
        return repository.findByUserIdOrderByTitleAsc(userId);
    }

    public Category add(Category category) {
        return repository.save(category);
    }

    public Category update(Category category) {
        return repository.save(category);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public List<Category> findByTitle(String title, Long userId) {
        return repository.findByTitle(title, userId);
    }
}
