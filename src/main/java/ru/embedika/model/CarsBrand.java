package ru.embedika.model;

import javax.persistence.*;

@Entity
@Table(name = "cars_brand")
public class CarsBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer Id;
    @Column(name = "name", unique = true, nullable = false, length = 150)
    private String name;

    public CarsBrand() {
    }

    public CarsBrand(Integer id, String name) {
        Id = id;
        this.name = name.trim();
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
