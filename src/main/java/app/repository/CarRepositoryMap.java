package app.repository;

import app.domain.Car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRepositoryMap implements CarRepository {

    private final Map<Long, Car> database = new HashMap<>();
    private long currentId;

    public CarRepositoryMap() {
        saveCar(new Car("Volkswagen", new BigDecimal(10000), 2010));
        saveCar(new Car("Mazda", new BigDecimal(30000), 2015));
        saveCar(new Car("Honda", new BigDecimal(50000), 2020));
    }


    @Override
    public List<Car> getAllCars() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Car getCarById(Long id) {
        if (database.isEmpty()) {
            System.out.println("Database is empty");
            return null;
        }
        if (!database.containsKey(id)) {
            System.out.println("Car id " + id + " not found");
            return null;
        }
        System.out.println("Car id " + id + " found");
        return database.get(id);
    }

    @Override
    public Car saveCar(Car car) {
        car.setId(++currentId);
        database.put(currentId, car);
        return car;
    }

    @Override
    public boolean putCar(Car car) {
        if(car.getId().equals(null)){
            System.out.println("Car id is null");
            return false;
        }
        if(!database.containsKey(car.getId())){
            System.out.println("Car id " + car.getId() + " not found");
            return false;
        }
        System.out.println("Car id " + car.getId() + " found and will be updated");
        database.put(car.getId(), car);
        return true;
    }

    @Override
    public boolean deleteCarById(Long id) {
        if(id.equals(null)){
            System.out.println("Car id is null");
            return false;
        }
        if(!database.containsKey(id)){
            System.out.println("Car id " + id + " is not found");
            return false;
        }
        System.out.println("Car id " + id + " found and will be deleted");
        Car car= database.remove(id);
        System.out.println( "Car " + car + " deleted");
        return true;
    }

//    public static void main(String[] args) {
//        CarRepositoryMap crm = new CarRepositoryMap();
//        crm.database.forEach((k, v) -> {
//            System.out.println("Key: " + k + " Value: " + v);
//        });
//        crm.getCarById(2L);
//         crm.deleteCarById(2L);
//         crm.getCarById(2L);
//        crm.database.forEach((k, v) -> {
//            System.out.println("Key: " + k + " Value: " + v);
//        });
//
//    }
}



