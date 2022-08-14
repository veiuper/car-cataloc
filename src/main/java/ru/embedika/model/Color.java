package ru.embedika.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "color")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Integer.class)
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JoinColumn()
    private Integer Id;
    //TODO Сделать по максимальной длине загруженных данных
    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "color", cascade = CascadeType.REMOVE)
    private List<Car> cars;

    public Color() {
    }

    public Color(Integer id, String name, List<Car> cars) {
        Id = id;
        this.name = name.trim();
        this.cars = cars;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
