package com.example.demo;

import org.springframework.data.repository.CrudRepository;

//CAR REPOSITORY HOLDS OUR CAR OBJECTS AND EXTENDS CRUD REPOSITORY FOR CREATE UPDATE DELETE

public interface CarRepository extends CrudRepository<Car, Long> {
}
