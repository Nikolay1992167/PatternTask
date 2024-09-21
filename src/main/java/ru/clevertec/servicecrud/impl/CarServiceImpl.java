package ru.clevertec.servicecrud.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.dao.CarDAO;
import ru.clevertec.dto.CarDto;
import ru.clevertec.dto.InfoCarDto;
import ru.clevertec.entity.Car;
import ru.clevertec.exception.NotFoundException;
import ru.clevertec.mapper.CarMapper;
import ru.clevertec.servicecrud.CarService;
import ru.clevertec.servicecrud.proxy.Log;
import ru.clevertec.util.PageChecker;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarMapper carMapper;
    private final CarDAO carDAO;

    /**
     * Find a car by ID
     *
     * @param id car ID
     * @return found car
     * @throws NotFoundException if not find
     */
    @Override
    @Log
    public InfoCarDto findById(UUID id) {
        Car car = carDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Car with " + id + " not found!"));

        return carMapper.toInfoCarDto(car);
    }

    /**
     * Returns all existing cars
     *
     * @return list of information by car
     */
    @Override
    @Log
    public List<InfoCarDto> getAll(Integer pageNumber, Integer pageSize) {
        int offset = PageChecker.checkPage(pageNumber, pageSize);
        List<Car> cars = carDAO.getAll(offset, pageSize);

        return cars.stream()
                .map(carMapper::toInfoCarDto)
                .toList();
    }

    /**
     * Create new car from DTO
     *
     * @param carDto DTO with information about creat
     */
    @Override
    @Log
    public UUID create(CarDto carDto) {
        Car carToSave = carMapper.toCar(carDto);
        Car car = carDAO.save(carToSave);

        return car.getId();
    }

    /**
     * Update also existing car from the information received in DTO
     *
     * @param id     ID of car for update
     * @param carDto DTO with information for update
     */
    @Override
    @Log
    public void update(UUID id, CarDto carDto) {
        carDAO.findById(id).ifPresentOrElse(
                car -> {
                    Car updatedCar = carMapper.merge(car, carDto);
                    carDAO.update(updatedCar);
                },
                () -> {
                    throw new NotFoundException("Car with %s not found!");
                }
        );
    }

    /**
     * Delete existing car
     *
     * @param id ID car for delete
     */
    @Override
    @Log
    public void delete(UUID id) {
        carDAO.delete(id);
    }
}