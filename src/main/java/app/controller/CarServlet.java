package app.controller;

import app.domain.Car;
import app.repository.CarRepository;
//import app.repository.CarRepositoryDB;
import app.repository.CarRepositoryHibernate;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/cars/*")
public class CarServlet extends HttpServlet {

    private final CarRepository repository = new CarRepositoryHibernate();

    // GET http://10.2.3.4:8080/cars
    // GET http://10.2.3.4:8080/cars?id=5

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Для получения из БД всех или одного автомобиля по id

        // req - объект запроса, из него мы можем извлечь всё, что прислал клиент
        // resp - объект ответа, который будет отправлен клиенту после того,
        //        как отработает наш метод. И мы можем в этот объект поместить всю
        //        информацию, которую мы хотим отправить клиенту в ответ на его запрос.
        String idParam = req.getParameter("id");


        if (idParam != null) {
            // here we get information about the car by id
            try {
                long id = Long.parseLong(idParam);
                Car car = repository.getCarById(id);
                if (car != null) {
                    //resp.setContentType("application/json");
                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(car);
                    resp.getWriter().write(json);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    //resp.getWriter().write("Car not found");
                }
            } catch (Exception e) {
                //TODO handle exception

            }

        } else {
            //if there is no id, we output all of the cars
            List<Car> cars = repository.getAllCars();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), cars);
        }


//        cars.forEach(x -> {
//            try {
//                resp.getWriter().write(x.toString() + "\n");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Для сохранения нового автомобиля в БД

        ObjectMapper mapper = new ObjectMapper();
        Car car = mapper.readValue(req.getReader(), Car.class);
        repository.saveCar(car);
        resp.getWriter().write(car.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Для изменения существующего автомобиля в БД
        ObjectMapper mapper = new ObjectMapper();
        Car car = mapper.readValue(req.getReader(), Car.class);
        boolean isUpdated = repository.putCar(car);
        if (isUpdated) {
            resp.getWriter().write("The car has been updated");
        } else {
            resp.getWriter().write("The car has not been updated");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Для удаления автомобиля из БД
        String pathInfo = req.getPathInfo(); // /:id
        String idParam = pathInfo.substring(1);

        boolean isDeleted = repository.deleteCarById(Long.parseLong(idParam));
        if (isDeleted) {
           resp.getWriter().write("The car has been deleted");
        } else {
            resp.getWriter().write("The car has not been deleted");
        }
    }
}
