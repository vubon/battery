package com.battery.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatteryStatistics {
    private int totalCapacity;
    private double averageCapacity;
}

