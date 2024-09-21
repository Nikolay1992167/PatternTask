package ru.clevertec.dao;



import ru.clevertec.entity.Car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarDAO {

    Optional<Car> findById(UUID id);

    List<Car> getAll(Integer pageNumber, Integer pageSize);

    Car save(Car car);

    Car update(Car car);

    void delete(UUID id);
}