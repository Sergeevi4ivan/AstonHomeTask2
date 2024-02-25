package ru.panas.restwithoutframework.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity book
 */

@Getter
@Setter
public class Book {

    private int id;

    private Person owner;

    private String title;

    private String author;

    private int yearProduction;

    public Book() {

    }

    public Book(String title, String author, int yearProduction) {
        this.title = title;
        this.author = author;
        this.yearProduction = yearProduction;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearProduction=" + yearProduction +
                '}';
    }


}
