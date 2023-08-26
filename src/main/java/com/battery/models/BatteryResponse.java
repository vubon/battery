package com.battery.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatteryResponse {
    private List<BatteryInfo> batteryInfoList;
    private BatteryStatistics statistics;
}
