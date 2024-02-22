package com.niit.bej.todoservice.domain;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Category {
    @Id
    private String title;
    private List<ToDo> toDoList;

    public Category() {
    }

    public Category(String title, List<ToDo> toDoList) {
        this.title = title;
        this.toDoList = toDoList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ToDo> getToDoList() {
        return toDoList;
    }

    public void setToDoList(List<ToDo> toDoList) {
        this.toDoList = toDoList;
    }

    @Override
    public String toString() {
        return "Category{" + "title='" + title + '\'' + ", toDoList=" + toDoList + '}';
    }
}
