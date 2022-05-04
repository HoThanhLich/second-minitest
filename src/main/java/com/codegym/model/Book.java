package com.codegym.model;

import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private int price;
    private String author;
    private String image;
    @ManyToOne
    private Category category;

    public Book() {
    }

    public Book(String name, int price, String author, String image, Category category) {
        this.name = name;
        this.price = price;
        this.author = author;
        this.image = image;
        this.category = category;
    }

    public Book(Long id, String name, int price, String author, String image, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
        this.image = image;
        this.category = category;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
