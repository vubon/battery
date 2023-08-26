package com.battery.controllers;

import com.battery.models.CustomResponse;
import com.battery.models.BatteryResponse;
import com.battery.models.Battery;
import com.battery.models.BatteryStatistics;
import com.battery.models.BatteryInfo;
import com.battery.services.BatteryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Battery APIs")
public class BatteryController {

    private final BatteryService batteryService;

    @Autowired
    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @PostMapping(value = "/batteries", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates batteries with provided details")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> createBattery(@RequestBody List<Battery> batteries) {

        // Data Validation
        if (batteries == null || batteries.isEmpty()) {
            return ResponseEntity.badRequest().body(new CustomResponse("Should have a non-empty request body", 400));
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        CopyOnWriteArrayList<Battery> validatedBatteries = new CopyOnWriteArrayList<>();
        Boolean hasError = null;

        for (Battery battery : batteries) {
            List<String> errors = new ArrayList<>();

            Set<ConstraintViolation<Battery>> violations = validator.validate(battery);
            for (ConstraintViolation<Battery> violation : violations) {
               errors.add(violation.getMessage());
               hasError = true;
            }
            battery.setErrors(errors);
            validatedBatteries.add(battery);
        }

        if (Boolean.TRUE.equals(hasError)){
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(validatedBatteries);
        }


        // Save Battery
        for (Battery battery : batteries) {
            battery.setId(UUID.randomUUID());
            this.batteryService.saveBattery(battery);
        }

        CustomResponse customResponse = new CustomResponse("Batteries created successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customResponse);
    }

    @GetMapping(value = "/batteries", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Query batteries with Postcode and Capacity")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> getBatteriesWithinPostcodeRangeAndCapacity(
            @RequestParam(required = false) String startPostcode,
            @RequestParam(required = false) String endPostcode,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity) {


        if (startPostcode == null || endPostcode == null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new CustomResponse("startPostcode and endPostcode are required", 400));
        }

        List<Battery> batteriesInRange;

        if (minCapacity != null && maxCapacity != null) {
            batteriesInRange = this.batteryService.getBatteriesByPostcodeAndCapacityRange(
                    minCapacity,
                    maxCapacity,
                    startPostcode,
                    endPostcode
            );
        } else {
            batteriesInRange = this.batteryService.getBatteriesByPostcodeRange(startPostcode, endPostcode);
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
