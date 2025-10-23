package com.nextjob.entities;

import jakarta.persistence.*;

@Table(name = "industries")
@Entity
public class Industry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column
    private String name;

    public  Industry() {}

    public Industry(String name) {
        this.name = name;
    }

    public Industry(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
