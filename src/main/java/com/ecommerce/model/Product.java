package com.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 45)
    @NotNull(message="Name is cannot be empty")
    @Column(name = "Name", nullable = false, length = 45)
    private String name;

    @NotNull
    @Column(name = "Price", nullable = false)
    private Integer price;

    @NotNull
    @Column(name = "Weight", nullable = false)
    private Integer weight;

    @Size(max = 45)
    @NotNull
    @Column(name = "Category", nullable = false, length = 45)
    private String category;

    @Size(max = 10000)
    @NotNull
    @Column(name = "image", nullable = false, length = 10000)
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}