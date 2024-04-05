package com.hughes.vms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hughes.vms.model.Vaccination_records;
import com.hughes.vms.services.VaccinationRecordsService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class VaccinationRecordsController {
    @Autowired
    VaccinationRecordsService vrService;

    @RequestMapping(value = "/vaccination-records", method = RequestMethod.GET)
    public List<Vaccination_records> readVaccinationRecords() {
        return vrService.getVaccinationRecords();
    }
    
    @RequestMapping(value = "/vaccination-records/phone/{phoneNumber}", method = RequestMethod.GET)
    public Vaccination_records readVaccinationRecordByPhoneNumber(@PathVariable String phoneNumber) {
    	return vrService.readByPhoneNumber(phoneNumber)
    			.orElseThrow(() -> new RuntimeException("Record not found with Phone Number: " + phoneNumber));
    }
    @PostMapping("/record/add")
    public Vaccination_records addVaccinationRecord(@RequestBody Vaccination_records vaccinationRecord) {
        return vrService.addVaccinationRecord(vaccinationRecord);
    }

    // You can add more request mapping methods here as needed
}
