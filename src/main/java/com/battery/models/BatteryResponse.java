package com.battery.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BatteryResponse {
    private List<BatteryInfo> batteryInfoList;
    private BatteryStatistics statistics;

    public BatteryResponse(List<BatteryInfo> batteryInfoList, BatteryStatistics statistics) {
        this.batteryInfoList = batteryInfoList;
        this.statistics = statistics;
    }
}
