package ru.embedika.model;

import javax.persistence.*;

@Entity
@Table(name = "color")
public class CarsColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer Id;
    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    public CarsColor() {
    }

    public CarsColor(Integer id, String name) {
        Id = id;
        this.name = name;
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
}
