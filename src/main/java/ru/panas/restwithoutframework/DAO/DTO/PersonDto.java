package ru.panas.restwithoutframework.DAO.DTO;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;
import ru.panas.restwithoutframework.models.Book;
import ru.panas.restwithoutframework.models.Passport;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Person DTO
 */

@Getter
@Setter
public class PersonDto {

    private int id;

    private String name;

    private int age;

    private List<Book> books;

    private Passport passport;

    public PersonDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public PersonDto(int id, String name, int age, List<Book> books, Passport passport) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.books = books;
        this.passport = passport;
    }

    public PersonDto() {
    }

    public List<Book> getBooks() {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        return books;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", books=" + books +
                ", passport=" + passport +
                '}';
    }

    //    public static Builder builder() {
//        return new Builder();
//    }
//
//    public PersonDto() {
//
//    }
//
//    /**
//     * Конструктор сделан закрытым, потому что объекты этого класса
//     * надо порождать таким образом:
//     * dto = CreateUserDto.builder().setName("John Doe").build()
//     */
//    private PersonDto(Builder builder) {
//        this.name = builder().personDto.name;
//        this.age = builder().personDto.age;
//        this.books = builder().personDto.books;
//        this.passport = builder.personDto.passport;
//    }
//
//    public List<Book> getBooks() {
//        if (this.books == null) {
//            this.books = new ArrayList<>();
//        }
//        return books;
//    }
//
//    @Override
//    public String toString() {
//        return "PersonDto{" +
//                "name='" + name + '\'' +
//                ", age=" + age +
//                ", books=" + books +
//                ", passport=" + passport +
//                '}';
//    }
//
//    /**
//     * Подсказываем механизму десериализации,
//     * что методы установки полей начинаются с set
//     */
//    @JsonPOJOBuilder(withPrefix = "set")
//    public static class Builder {
//
//        private PersonDto personDto;
//
//        public Builder() {
//            personDto = new PersonDto();
//        }
//
//        public Builder setId(int id) {
//            personDto.id = id;
//            return this;
//        }
//
//        public Builder setName(String name) {
//            personDto.name = name;
//            return this;
//        }
//
//        public Builder setAge(int age) {
//            personDto.age = age;
//            return this;
//        }
//
//        public Builder setBooks(List<Book> books) {
//            if (personDto.books == null) {
//                personDto.books = new ArrayList<>();
//            }
//            personDto.books = books;
//            return this;
//        }
//
//        public Builder setPassport(Passport passport) {
//            personDto.passport = passport;
//            return this;
//        }
//
//        public PersonDto build() {
//            return personDto;
//        }

//        private int id;
//        private String name;
//
//        private int age;
//
//        private List<Book> books;
//
//        private Passport passport;
//
//
//        public Builder setId(int id) {
//            this.id = id;
//            return this;
//        }
//
//        public Builder setName(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public Builder setAge(int age) {
//            this.age = age;
//            return this;
//        }
//
//        public Builder setBooks(List<Book> books) {
//            if (this.books == null) {
//                this.books = new ArrayList<>();
//            }
//            this.books = books;
//            return this;
//        }
//
//        public Builder setPassport(Passport passport) {
//            this.passport = passport;
//            return this;
//        }
//
//        public PersonDto build() {
//            return new PersonDto(this);
//        }
//    }
}
