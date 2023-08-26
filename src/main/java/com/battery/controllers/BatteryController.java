package com.battery.controllers;

import com.battery.models.CustomResponse;
import com.battery.models.BatteryResponse;
import com.battery.models.Battery;
import com.battery.models.BatteryStatistics;
import com.battery.models.BatteryInfo;
import com.battery.services.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class BatteryController {

    @Autowired
    private BatteryService batteryService;

    @PostMapping(value = "/batteries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse> createBattery(@RequestBody(required = false) List<Battery> batteries) {
        if (batteries == null){
            return ResponseEntity.badRequest().body(new CustomResponse("Should have request body", 400));
        }
        for (Battery battery : batteries) {
            battery.setId(UUID.randomUUID());
            batteryService.saveBattery(battery);
        }

        CustomResponse customResponse = new CustomResponse("Batteries created successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }

    @GetMapping(value = "/batteries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBatteriesWithinPostcodeRangeAndCapacity(
            @RequestParam(required = false) String startPostcode,
            @RequestParam(required = false) String endPostcode,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity) {


        if (startPostcode == null || endPostcode == null) {
            return ResponseEntity.badRequest().body(new CustomResponse("startPostcode and endPostcode are required", 400));
        }

        List<Battery> batteriesInRange;

        if (minCapacity != null && maxCapacity != null) {
            batteriesInRange = batteryService.getBatteriesByPostcodeRangeAndCapacity(
                    minCapacity,
                    maxCapacity,
                    startPostcode,
                    endPostcode
            );
        } else {
            batteriesInRange = batteryService.getBatteriesByPostcodeRange(startPostcode, endPostcode);
        }

        List<BatteryInfo> batteryInfoList = batteriesInRange.stream()
                .map(battery -> new BatteryInfo(battery.getName(), battery.getCapacity(), battery.getPostcode(), battery.getId()))
                .sorted(Comparator.comparing(BatteryInfo::getName))
                .collect(Collectors.toList());

        int totalCapacity = batteriesInRange.stream().mapToInt(Battery::getCapacity).sum();
        double averageCapacity = batteriesInRange.stream().mapToInt(Battery::getCapacity).average().orElse(0.0);

        BatteryStatistics statistics = new BatteryStatistics(totalCapacity, averageCapacity);

        BatteryResponse response = new BatteryResponse(batteryInfoList, statistics);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
