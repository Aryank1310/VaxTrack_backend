package com.hughes.vms.controller;


import com.hughes.vms.model.Vaccines;
import com.hughes.vms.services.VaccinesService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class VaccinesController {

    @Autowired
    VaccinesService vaccinesService;

    @PostMapping("/vaccines/register")
    public Vaccines insertVaccine(@RequestBody Vaccines vaccine) {
        return vaccinesService.insertVaccine(vaccine);
    }
    
    @RequestMapping(value="/vaccines",method=RequestMethod.GET)
	public List<Vaccines> readVaccines(){
		return vaccinesService.getVaccines();
	}
   
    @PostMapping("/vaccines/register/bulk")
    public ResponseEntity<String> registerBulkVaccines(@RequestParam String manufacturer,
                                                       @RequestParam Date dateOfManufacture,
                                                       @RequestParam Date expiryDate,
                                                       @RequestParam int numberOfVaccines) {
        try {
            vaccinesService.saveVaccinesInBulk(manufacturer, dateOfManufacture, expiryDate, numberOfVaccines);
            return ResponseEntity.status(HttpStatus.CREATED).body("Vaccines added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding vaccines");
        }
    }


}
