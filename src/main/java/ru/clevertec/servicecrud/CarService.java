package ru.clevertec.servicecrud;


import ru.clevertec.dto.CarDto;
import ru.clevertec.dto.InfoCarDto;

import java.util.List;
import java.util.UUID;

public interface CarService {

    InfoCarDto findById(UUID id);

    List<InfoCarDto> getAll(Integer pageNumber, Integer pageSize);

    UUID create(CarDto carDto);

    void update(UUID id, CarDto carDto);

    void delete(UUID id);
}