package ru.panas.restwithoutframework.DAO.DTO;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

/**
 * Class Passport DTO
 */

@Getter
@Setter
public class PassportDto {

    private int passportId;

    private String address;

    private int number;

    public static Builder builder() {
        return new Builder();
    }

    public PassportDto() {
    }

    private PassportDto(Builder builder) {
        this.passportId = passportId;
        this.address = address;
        this.number = number;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {

        private int passportId;

        private String address;

        private int number;

        public Builder setPassportId(int passportId) {
            this.passportId = passportId;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setNumber(int number) {
            this.number = number;
            return this;
        }

        public PassportDto build() {
            return new PassportDto(this);
        }
    }
}
