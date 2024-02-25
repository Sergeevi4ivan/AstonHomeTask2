package ru.panas.restwithoutframework.DAO.DTO;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

/**
 *  Class Book DTO
 */
@Getter
@Setter
public class BookDto {

    private int id;

    private String title;

    private String author;

    private int yearProduction;

    public static Builder builder() {
        return new Builder();
    }

    public BookDto() {
    }

    /**
     * Конструктор сделан закрытым, потому что объекты этого класса
     * надо порождать таким образом:
     * dto = CreateUserDto.builder().setName("John Doe").build()
     */
    private BookDto(Builder builder) {
        this.title = title;
        this.author = author;
        this.yearProduction = yearProduction;
    }



    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearProduction=" + yearProduction +
                '}';
    }

    /**
     * Подсказываем механизму десериализации,
     * что методы установки полей начинаются с set
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        private int id;

        private String title;

        private String author;

        private int yearProduction;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setYearProduction(int yearProduction) {
            this.yearProduction = yearProduction;
            return this;
        }

        public BookDto build() {
            return new BookDto(this);
        }
    }
}
