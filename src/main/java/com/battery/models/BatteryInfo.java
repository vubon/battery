package com.battery.models;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BatteryInfo {
    private UUID id;
    private String name;
    private int capacity;
    private String postcode;

    public BatteryInfo(String name, int capacity, String postcode, UUID id) {
        this.name = name;
        this.capacity = capacity;
        this.postcode = postcode;
        this.id = id;
    }
}