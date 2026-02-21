package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CarController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/predict")
    public String predict(
            @RequestParam String brand,
            @RequestParam int vehicle_age,
            @RequestParam int km_driven,
            @RequestParam String seller_type,
            @RequestParam String fuel_type,
            @RequestParam String transmission_type,
            @RequestParam double mileage,
            @RequestParam int engine,
            @RequestParam double max_power,
            @RequestParam int seats,
            Model model
    ) {

        String flaskUrl = "https://car-price-ml-api.onrender.com/predict";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("brand", brand);
        request.put("vehicle_age", vehicle_age);
        request.put("km_driven", km_driven);
        request.put("seller_type", seller_type);
        request.put("fuel_type", fuel_type);
        request.put("transmission_type", transmission_type);
        request.put("mileage", mileage);
        request.put("engine", engine);
        request.put("max_power", max_power);
        request.put("seats", seats);

        Map response = restTemplate.postForObject(flaskUrl, request, Map.class);

        Double price = Double.parseDouble(response.get("predicted_price").toString());
        String formattedPrice = String.format("%,.2f,", price);
        model.addAttribute("price", formattedPrice);


        return "result";
    }
}
