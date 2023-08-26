package com.battery.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatteryStatistics {
    private int totalCapacity;
    private double averageCapacity;

    public BatteryStatistics(int totalCapacity, double averageCapacity) {
        this.totalCapacity = totalCapacity;
        this.averageCapacity = averageCapacity;
    }
}

