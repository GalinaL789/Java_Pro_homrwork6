package app.repository;

import app.domain.Car;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static app.constants.Constants.*;

public class CarRepositoryDB implements CarRepository {

    private Connection getConnection() {
        try {
            Class.forName(DB_DRIVER_PATH);

            // http://10.2.3.4:8080/cars?id=3
            // jdbc:postgresql://10.2.3.4:5432/g_33_cars?user=postgres&password=ppp77777

            String dbUrl = String.format("%s%s?user=%s&password=%s",
                    DB_ADDRESS, DB_NAME, DB_USERNAME, DB_PASSWORD);

            return DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM cars";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                //parse resultSet по полям
                Long id = resultSet.getLong("id");
                String brand = resultSet.getString("brand");
                BigDecimal price = resultSet.getBigDecimal("price");
                int year = resultSet.getInt("year");
                Car car = new Car(id, brand, price, year);
                cars.add(car);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    @Override
    public Car getCarById(Long id) {
        try (Connection connection = getConnection()) {
            // TODO домашнее задание
            String query = String.format("SELECT * FROM cars WHERE id='%s'", id);
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                Long carId = resultSet.getLong("id");
                String brand = resultSet.getString("brand");
                BigDecimal price = resultSet.getBigDecimal("price");
                int year = resultSet.getInt("year");
                Car car = new Car(carId, brand, price, year);
                return car;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Car saveCar(Car car) {
        try (Connection connection = getConnection()) {

            String query = String.format("INSERT INTO cars (brand, price, year) VALUES ('%s', '%s', '%d')",
                    car.getBrand(), car.getPrice(), car.getYear());

            Statement statement = connection.createStatement();
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

            Long id = resultSet.getLong(1);
            car.setId(id);
            return car;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//    @Override
//    public Car saveCar(Car car) {
//        String query = "INSERT INTO car (brand, price, year) VALUES (?, ?, ?)";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//
//            statement.setString(1, car.getBrand());
//            statement.setDouble(2, car.getPrice());
//            statement.setInt(3, car.getYear());
//
//            statement.executeUpdate();
//
//            try (ResultSet resultSet = statement.getGeneratedKeys()) {
//                if (resultSet.next()) {
//                    Long id = resultSet.getLong(1);
//                    car.setId(id);
//                }
//            }
//
//            return car;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


    @Override
    public boolean putCar(Car car) {
        try (Connection connection = getConnection()) {

            String query = String.format("UPDATE cars SET price='%s' WHERE id='%s'",
                    car.getPrice(), car.getId());
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (Exception e) {
            //TODO handle exception
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean deleteCarById(Long id) {
        try (Connection connection = getConnection()) {
// check if the car with id exists
            if (this.getCarById(id) == null) {
                return false;
            }
            String query = String.format("DELETE FROM cars WHERE id='%s'", id);
            Statement statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
