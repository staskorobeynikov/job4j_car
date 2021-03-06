package ru.job4j.carmarket.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "carbodies")
public class CarBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "color")
    private String color;

    @Column(name = "count_doors")
    private int countDoor;

    public CarBody() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCountDoor() {
        return countDoor;
    }

    public void setCountDoor(int countDoor) {
        this.countDoor = countDoor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarBody carBody = (CarBody) o;
        return id == carBody.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("CarBody: id=%s, type=%s, color=%s, countDoor=%s.", id, type, color, countDoor);
    }
}
