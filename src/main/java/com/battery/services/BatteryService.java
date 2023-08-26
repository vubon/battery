package com.battery.services;

import com.battery.models.Battery;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatteryService {

    private final List<Battery> batteries = new ArrayList<>();

    public List<Battery> saveBattery(Battery battery) {
        batteries.add(battery);
        return batteries;
    }

    public List<Battery> findBatteriesByPostcodeRange(String startPostcode, String endPostcode) {
        List<Battery> batteriesInRange = new ArrayList<>();
        for (Battery battery : batteries) {
            if (battery.getPostcode().compareTo(startPostcode) >= 0 && battery.getPostcode().compareTo(endPostcode) <= 0) {
                batteriesInRange.add(battery);
            }
        }
        return batteriesInRange;
    }

    public List<Battery> getBatteriesByPostcodeRangeAndCapacity(
            int minCapacity,
            int maxCapacity,
            String startPostcode,
            String endPostcode) {

        List<Battery> filteredBatteries = new ArrayList<>();

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
