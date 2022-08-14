package ru.embedika.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars_brand")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Integer.class)
public class CarsBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer Id;
    @OneToMany(mappedBy = "carsBrand", cascade = CascadeType.REMOVE)
    private List<Car> cars;
    //TODO Сделать по максимальной длине загруженных данных
    @Column(name = "name", unique = true, nullable = false, length = 150)
    private String name;

    public CarsBrand() {
    }

    public CarsBrand(Integer id, List<Car> cars, String name) {
        Id = id;
        this.cars = cars;
        this.name = name.trim();
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
