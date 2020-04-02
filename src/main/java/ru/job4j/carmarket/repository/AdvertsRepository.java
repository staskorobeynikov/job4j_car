package ru.job4j.carmarket.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.job4j.carmarket.model.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdvertsRepository implements Repository<Advert, Car, User> {

    private static final Logger LOG = LogManager.getLogger(AdvertsRepository.class.getName());

    private final SessionFactory factory = new Configuration()
            .configure()
            .buildSessionFactory();

    @Override
    public List<Advert> findAll() {
        List<Advert> result = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("FROM ru.job4j.carmarket.model.Advert").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Advert> showWithPhoto() {
        List<Advert> result = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("FROM ru.job4j.carmarket.model.Advert WHERE image_name != ''").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Advert> showLastDay() {
        List<Advert> result = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("FROM ru.job4j.carmarket.model.Advert "
                    + "WHERE extract(day from created_date) > extract(day from current_date) - 1").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Advert> showWithSpecificMark(Mark mark) {
        List<Advert> result = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("FROM ru.job4j.carmarket.model.Advert as adv "
                    + "join fetch adv.car cars "
                    + "join fetch cars.mark WHERE name = :markName");
            query.setParameter("markName", mark.getName());
            result = query.list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> result = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("FROM ru.job4j.carmarket.model.Car").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<User> getUsers() {
        List<User> result = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("FROM ru.job4j.carmarket.model.User").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Advert> getAdvertsUser(User user) {
        List<Advert> result = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("FROM ru.job4j.carmarket.model.Advert WHERE owner_id = :id")
                    .setParameter("id", user.getId())
                    .list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void updateStatus(Advert advert) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("UPDATE ru.job4j.carmarket.model.Advert SET status = :done WHERE id = :id");
            query.setParameter("done", advert.isStatus());
            query.setParameter("id", advert.getId());
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public void addNewAdvert(CarBody carBody, Engine engine, Transmission transmission,
                             Mark mark, Model model, User user, Car car, Advert advert) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        User owner;
        try {
            session.save(carBody);
            car.setCarBody(carBody);

            session.save(engine);
            car.setEngine(engine);

            session.save(transmission);
            car.setTransmission(transmission);

            session.save(mark);
            car.setMark(mark);

            session.save(model);
            car.setModel(model);

            session.save(car);

            Car car1 = session.get(Car.class, car.getId());

            owner = session.load(User.class, user.getId());

            advert.setCar(car1);

            advert.setOwner(owner);

            session.save(advert);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public User addUser(User user, Account account) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(account);

            user.setAccount(account);
            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return user;
    }
}
