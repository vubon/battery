package com.battery.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Battery {
    private String name;
    private String postcode;
    private int capacity;

    public Battery(String name, String postcode, int capacity) {
        this.name = name;
        this.postcode = postcode;
        this.capacity = capacity;
    }
}
