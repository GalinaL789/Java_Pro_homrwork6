//package app.repository;
//
//import app.domain.Car;
//import app.repository.CarRepository;
//
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.List;
//
//import static app.constants.Constants.*;
//
//public class CarRepositoryDB implements CarRepository {
//
//    private Connection getConnection() {
//        try {
//            Class.forName(DB_DRIVER_PATH);
//
//            // http://10.2.3.4:8080/cars?id=3
//            // jdbc:postgresql://10.2.3.4:5432/g_33_cars?user=postgres&password=ppp77777
//
//            String dbUrl = String.format("%s%s?user=%s&password=%s",
//                    DB_ADDRESS, DB_NAME, DB_USERNAME, DB_PASSWORD);
//
//            return DriverManager.getConnection(dbUrl);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    @Override
//    public List<Car> getAllCars() {
//        return List.of();
//    }
//
//    @Override
//    public Car getCarById(Long id) {
//        try(Connection connection=getConnection())
//        {
//            String query=String.format("SELECT * FROM car WHERE id=%d", id);
//            ResultSet resultSet=connection.createStatement().executeQuery(query);
//            if(resultSet.next())
//            {
//                String brand=resultSet.getString("brand");
//                BigDecimal price=resultSet.getBigDecimal("price");
//                int year=resultSet.getInt("year");
//                return new Car(id, brand, price, year);
//
//            }
//        }
//        catch(Exception e)
//        {
//            throw new RuntimeException(e)
//        }
//        return null;
//    }
//
//    @Override
//    public Car saveCar(Car car) {
//        return null;
//    }
//
//    @Override
//    public boolean putCar(Car car) {
//        return false;
//    }
//
//    @Override
//    public boolean deleteCarById(Long id) {
//        return false;
//    }
//}
//
//
