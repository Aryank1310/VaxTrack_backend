package com.hughes.vms.controller;

import com.hughes.vms.model.Vaccination_centers;
import com.hughes.vms.services.VaccinationCentersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class VaccinationCentersController {

    @Autowired
    VaccinationCentersService vcService;

    @GetMapping("/centers")
    public List<Vaccination_centers> getAllCenters() {
        return vcService.getAllCenters();
    }

    @GetMapping("/centers/search")
    public List<Vaccination_centers> searchCenters(@RequestParam int pincode) {
        return vcService.readByPincode(pincode);
    }
    
    @PostMapping("/centers/register")
    public Vaccination_centers registerVaccinationCenter(@RequestBody Vaccination_centers center) {
        return vcService.registerVaccinationCenter(center.getName(), center.getAddress(),
                center.getPhoneNumber(), center.getPincode(), center.getState(), center.getDistrict());
    }

}
