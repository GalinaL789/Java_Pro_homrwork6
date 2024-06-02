 package app.repository;

import app.domain.Car;

import java.util.List;

public interface CarRepository {

    List<Car> getAllCars();

    Car getCarById(Long id);

    Car saveCar(Car car);

    boolean putCar(Car car);

    boolean deleteCarById(Long id);
}

