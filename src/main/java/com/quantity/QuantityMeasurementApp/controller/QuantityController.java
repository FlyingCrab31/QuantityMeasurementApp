package com.quantity.QuantityMeasurementApp.controller;

import com.quantity.QuantityMeasurementApp.dto.QuantityDTO;
import com.quantity.QuantityMeasurementApp.service.QuantityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quantity")
public class QuantityController {

    private final QuantityService service;

    public QuantityController(QuantityService service) {
        this.service = service;
    }

    // ✅ ADD
    @PostMapping("/add")
    public QuantityDTO add(@RequestBody QuantityDTO[] quantities) {
        if (quantities.length != 2) {
            throw new IllegalArgumentException("Two quantities required for addition");
        }
        return service.add(quantities[0], quantities[1]);
    }

    // ✅ COMPARE
    @PostMapping("/compare")
    public boolean compare(@RequestBody QuantityDTO[] quantities) {
        if (quantities.length != 2) {
            throw new IllegalArgumentException("Two quantities required for comparison");
        }
        return service.compare(quantities[0], quantities[1]);
    }

    // ✅ CONVERT
    @PostMapping("/convert")
    public QuantityDTO convert(@RequestBody QuantityDTO request,
                               @RequestParam String targetUnit) {
        return service.convert(request, targetUnit);
    }
}