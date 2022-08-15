package ru.embedika.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "car")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "carNumber", scope = String.class)
public class Car {
    @Id
    @Column(name = "car_number", unique = true, nullable = false, length = 12)
    private String carNumber;
    @ManyToOne()
    @JoinColumn(name = "cars_brand")
    private CarsBrand carsBrand;
    @ManyToOne()
    @JoinColumn(name = "cars_color")
    private CarsColor carsColor;
    @Temporal(TemporalType.DATE)
    @Column(name = "release_year", nullable = false)
    private Date releaseYear;

    public Car() {
    }

    public Car(String carNumber, CarsBrand carsBrand, CarsColor carsColor, Date releaseYear) {
        this.carNumber = carNumber.trim().toUpperCase();
        this.carsBrand = carsBrand;
        this.carsColor = carsColor;
        this.releaseYear = releaseYear;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public CarsBrand getCarsBrand() {
        return carsBrand;
    }

    public void setCarsBrand(CarsBrand carsBrand) {
        this.carsBrand = carsBrand;
    }

    public CarsColor getCarsColor() {
        return carsColor;
    }

    public void setCarsColor(CarsColor carsColor) {
        this.carsColor = carsColor;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }
}
