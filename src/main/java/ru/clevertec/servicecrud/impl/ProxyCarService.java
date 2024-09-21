package ru.clevertec.servicecrud.impl;

import org.springframework.stereotype.Service;
import ru.clevertec.dto.CarDto;
import ru.clevertec.dto.InfoCarDto;
import ru.clevertec.servicecrud.CarService;
import ru.clevertec.servicecrud.proxy.Log;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

@Service
public class ProxyCarService implements CarService {

    private final CarServiceImpl carServiceImpl;

    public ProxyCarService(CarServiceImpl carServiceImpl) {
        this.carServiceImpl = carServiceImpl;
    }

    @Override
    public InfoCarDto findById(UUID id) {
        return invokeWithLogging("findById", new Class[]{UUID.class}, new Object[]{id});
    }

    @Override
    public List<InfoCarDto> getAll(Integer pageNumber, Integer pageSize) {
        return invokeWithLogging("getAll", new Class[]{Integer.class, Integer.class}, new Object[]{pageNumber, pageSize});
    }

    @Override
    public UUID create(CarDto carDto) {
        return invokeWithLogging("create", new Class[]{CarDto.class}, new Object[]{carDto});
    }

    @Override
    public void update(UUID id, CarDto carDto) {
        invokeWithLogging("update", new Class[]{UUID.class, CarDto.class}, new Object[]{id, carDto});
    }

    @Override
    public void delete(UUID id) {
        invokeWithLogging("delete", new Class[]{UUID.class}, new Object[]{id});
    }

    @SuppressWarnings("unchecked")
    private <T> T invokeWithLogging(String methodName, Class<?>[] paramTypes, Object[] args) {
        try {
            Method method = CarServiceImpl.class.getMethod(methodName, paramTypes);
            if (method.isAnnotationPresent(Log.class)) {
                System.out.println("Starting method: " + method.getDeclaringClass().getName() + "." + methodName);
                T result = (T) method.invoke(carServiceImpl, args);
                System.out.println("Finished method: " + method.getDeclaringClass().getName() + "." + methodName);
                return result;
            } else {
                return (T) method.invoke(carServiceImpl, args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}