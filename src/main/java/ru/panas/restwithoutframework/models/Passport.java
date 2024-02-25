package ru.panas.restwithoutframework.models;

import lombok.Getter;
import lombok.Setter;


/**
 * Entity passport
 */
@Getter
@Setter
public class Passport {

    private int passportId;

    private String address;

    private int number;

    private Person person;

    public Passport(String address, int number) {
        this.address = address;
        this.number = number;
    }

    public Passport() {
    }

    @Override
    public String toString() {
        return "Passport{" +
                "passportId=" + passportId +
                ", adress='" + address + '\'' +
                ", number=" + number +
                '}';
    }
}
