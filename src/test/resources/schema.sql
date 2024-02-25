DROP ALL OBJECTS;

CREATE SCHEMA IF NOT EXISTS db;

CREATE TABLE person(
                       id serial primary key ,
                       name varchar(100) not null ,
                       age int not null

);

CREATE TABLE books(
                      bookId serial primary key ,
                      title varchar(100) not null ,
                      author varchar(100) not null ,
                      yearProduction varchar(100) not null ,
                      owner varchar(100),
                      personId int REFERENCES person(id)
);

CREATE TABLE passport(
                         personId int REFERENCES person(id),
                         number int not null ,
                         address varchar(100) not null
);
