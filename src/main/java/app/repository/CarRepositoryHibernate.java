package app.repository;

import app.domain.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CarRepositoryHibernate implements CarRepository {
    private final EntityManager entityManager;

    public CarRepositoryHibernate() {
        entityManager = new Configuration()
                .configure("hibernate/postgres.cfg.xml")
                .buildSessionFactory().createEntityManager();
    }

    @Override
    public List<Car> getAllCars() {
        String hql = "from Car order by price asc";
        return entityManager.createQuery(hql, Car.class).getResultList();
    }

    @Override
    public Car getCarById(Long id) {
        return entityManager.find(Car.class, id);
    }

    @Override
    public Car saveCar(Car car) {

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(car);
            transaction.commit();
            return car;
        } catch (Exception e) {
            if (transaction.isActive()) ;
            {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction cancelled");
        }
    }

    @Override
    public boolean putCar(Car car) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            // entityManager.merge(car);// here one can change all parameters/
            Car carFromDb = getCarById(car.getId());
            carFromDb.setPrice(car.getPrice());
            //entityManager.persist(carFromDb);// this is not compulsory
            transaction.commit();

            return true;
        } catch (Exception e) {
            if (transaction.isActive()) ;
            {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction cancelled");
        }

    }

    @Override
    public boolean deleteCarById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(getCarById(id));
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) ;
            {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction cancelled");
        }
    }
}



