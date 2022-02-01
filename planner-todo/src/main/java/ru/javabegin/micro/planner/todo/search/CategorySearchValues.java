package ru.javabegin.micro.planner.todo.search;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategorySearchValues {

    private String title;
    private Long userId;
}
