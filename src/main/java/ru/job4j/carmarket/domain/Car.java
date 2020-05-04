package ru.job4j.carmarket.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "mark_id", foreignKey = @ForeignKey(name = "mark_id_fk"))
    private Mark mark;

    @ManyToOne
    @JoinColumn(name = "model_id", foreignKey = @ForeignKey(name = "model_id_fk"))
    private Model model;

    @Column(name = "mile_age")
    private int mileAge;

    @Column(name = "created")
    private Timestamp created;

    @ManyToOne
    @JoinColumn(name = "transmission_id", foreignKey = @ForeignKey(name = "transmission_id_fk"))
    private Transmission transmission;

    @ManyToOne
    @JoinColumn(name = "car_body_id", foreignKey = @ForeignKey(name = "car_body_id_fk"))
    private CarBody carBody;

    @ManyToOne
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "engine_id_fk"))
    private Engine engine;

    public Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public int getMileAge() {
        return mileAge;
    }

    public void setMileAge(int mileAge) {
        this.mileAge = mileAge;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
                "Car: id=%s, mark=%s, model=%s, mileAge=%s, created=%s, transmission=%s, carBody=%s, engine=%s",
                id,
                mark,
                model,
                mileAge,
                created,
                transmission,
                carBody,
                engine
        );
    }
}
