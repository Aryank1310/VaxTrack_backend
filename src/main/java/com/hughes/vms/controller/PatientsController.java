package com.hughes.vms.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hughes.vms.model.Patients;
import com.hughes.vms.services.PatientsService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PatientsController {
	@Autowired
	PatientsService pService;
	
	@RequestMapping(value="/patients",method=RequestMethod.GET)
	public List<Patients> readPatients(){
		return pService.getPatients();
	}
	
    @RequestMapping(value = "/patients/{patientId}", method = RequestMethod.GET)
    public Patients readPatientById(@PathVariable Long patientId) {
        return pService.readByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
    }

    @RequestMapping(value = "/patients/phone/{phoneNumber}", method = RequestMethod.GET)
    public Patients readPatientByPhoneNo(@PathVariable String phoneNumber) {
        return pService.readByPhoneNumber(phoneNumber);
    }

    @RequestMapping(value = "/patients/register", method = RequestMethod.POST)
    public Patients registerPatient(@RequestBody Patients patient) {
        return pService.registerPatient(patient.getFirstName(), patient.getLastName(), patient.getAddress(),
                patient.getPhoneNumber(), patient.getEmail(), patient.getDob(), patient.getGender());
    }
}
