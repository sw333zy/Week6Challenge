package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//create our car class which will make a table of car with our given attributes due to the
//entity annotation
// CAR owns the relationship b/w car and category

@Entity
public class Car {


    @Id
    //auto incriment our id numbers
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //none of these can be null
    @NotNull
    private String manufacturer;
    @NotNull
    private String model;
    @NotNull
    private int year;
    @NotNull
    private double msrp;
//    private String categoryName;
    private String carpic;

    //there are many different types of cars to one CATEGORY
    //Fetch type EAGAR means
    @ManyToOne(fetch = FetchType.EAGER)
    //join the column on the CATEGORY ID which is UNIQUE
    @JoinColumn(name = "category_id")
    private Category category;

    //Create our constructor
    public Car() {
    }

    //GETTERS AND SETTERS
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMsrp() {
        return msrp;
    }

    public void setMsrp(double msrp) {
        this.msrp = msrp;
    }

    public String getCarpic() {
        return carpic;
    }

    public void setCarpic(String carpic) {
        this.carpic = carpic;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
