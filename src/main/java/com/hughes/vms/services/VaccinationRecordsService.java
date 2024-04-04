package com.hughes.vms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hughes.vms.model.Patients;
import com.hughes.vms.model.Vaccination_records;
import com.hughes.vms.repository.VaccinationRecordsRepository;

@Service
public class VaccinationRecordsService {
    @Autowired
    VaccinationRecordsRepository vrRepo;
    
    @Autowired
    PatientsService patientsService;
    
    public List<Vaccination_records> getVaccinationRecords() {
        return vrRepo.findAll();
    }
    
    public Optional<Vaccination_records> readByPhoneNumber(String phoneNumber) {
    	Patients patient = patientsService.readByPhoneNumber(phoneNumber);
        if (patient != null) {
            return vrRepo.findById(patient.getPatientId());
        }
        return null;
    }
}
