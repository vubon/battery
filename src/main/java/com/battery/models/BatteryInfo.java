package com.battery.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatteryInfo {
    private String name;
    private int capacity;
    private String postcode;

    public BatteryInfo(String name, int capacity, String postcode) {
        this.name = name;
        this.capacity = capacity;
        this.postcode = postcode;
    }
}