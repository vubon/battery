package com.battery;

import com.battery.models.Battery;
import com.battery.security.JwtHelper;
import com.battery.services.BatteryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BatteryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private BatteryService batteryService;

    @Test
    public void testCreateBatteries() throws Exception {
        // Generate a JWT token using your JwtHelper class
        UserDetails userDetails = userDetailsService.loadUserByUsername("vubon");
        String token = jwtHelper.generateToken(userDetails);

        // Prepare mock data
        List<Battery> batteries = new ArrayList<>();
        batteries.add(new Battery("Battery 1", "12345", 1000));
        batteries.add(new Battery("Battery 2", "67890", 2000));
        // Mock batteryService.saveBattery to return the saved batteries
        Mockito.when(batteryService.saveBattery(Mockito.any(Battery.class)))
                .thenReturn(batteries);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/batteries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(batteries)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Add more assertions based on your requirements
    }

    @Test
    public void testGetBatteriesWithinPostcodeRange() throws Exception {
        // Generate a JWT token using your JwtHelper class
        UserDetails userDetails = userDetailsService.loadUserByUsername("vubon");
        String token = jwtHelper.generateToken(userDetails);

        // Prepare mock data
        List<Battery> batteries = new ArrayList<>();
        batteries.add(new Battery("Battery 1", "12345", 1000));
        batteries.add(new Battery("Battery 2", "67890", 2000));
        // Mock batteryService.getBatteriesByPostcodeRange to return the mock batteries
        Mockito.when(batteryService.findBatteriesByPostcodeRange(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(batteries);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/batteries")
                        .header("Authorization", "Bearer " + token)
                        .param("startPostcode", "12345")
                        .param("endPostcode", "67890"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.batteryInfoList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statistics.totalCapacity", Matchers.is(3000)));

        // Add more assertions based on your requirements
    }
}
