package com.battery.services;

import com.battery.models.Battery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class BatteryService {

    private final List<Battery> batteries = new CopyOnWriteArrayList<>();

    public void saveBattery(Battery battery) {
        batteries.add(battery);
    }

    public List<Battery> getBatteriesByPostcodeRange(String startPostcode, String endPostcode) {
        List<Battery> batteriesInRange = new CopyOnWriteArrayList<>();
        for (Battery battery : batteries) {
            if (isPostcodeInRange(battery, startPostcode, endPostcode)) {
                batteriesInRange.add(battery);
            }
        }
        return batteriesInRange;
    }

    public List<Battery> getBatteriesByPostcodeAndCapacityRange(
            int minCapacity,
            int maxCapacity,
            String startPostcode,
            String endPostcode) {

        List<Battery> filteredBatteries = new CopyOnWriteArrayList<>();

        for (Battery battery : batteries) {
            int capacity = battery.getCapacity();
            // Check if the capacity is within the specified range
            if (capacity >= minCapacity && capacity <= maxCapacity) {
                // Check if the postcode is within the specified range
                if (isPostcodeInRange(battery, startPostcode, endPostcode)) {
                    filteredBatteries.add(battery);
                }
            }
        }

        return filteredBatteries;
    }

    private boolean isPostcodeInRange(Battery battery, String startPostcode, String endPostcode) {
        String batteryPostcode = battery.getPostcode();
        return batteryPostcode.compareTo(startPostcode) >= 0 && batteryPostcode.compareTo(endPostcode) <= 0;
    }


}
