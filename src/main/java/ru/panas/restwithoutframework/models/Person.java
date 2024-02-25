package ru.panas.restwithoutframework.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity person
 */

@Getter
@Setter
public class Person {

    private int id;

    private String name;

    private int age;

    private List<Book> books;

    private Passport passport;

    public Person() {

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public List<Book> getBooks() {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        return books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
