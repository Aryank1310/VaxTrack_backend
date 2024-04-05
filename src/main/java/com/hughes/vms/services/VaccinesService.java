package com.hughes.vms.services;

import com.hughes.vms.model.Patients;
import com.hughes.vms.model.Vaccines;
import com.hughes.vms.repository.VaccinesRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinesService {

    @Autowired
    VaccinesRepository vaccinesRepository;

    public Vaccines insertVaccine(Vaccines vaccine) {
        return vaccinesRepository.save(vaccine);
    }
    public List<Vaccines> getVaccines() {
		return vaccinesRepository.findAll();
	}
    public static Long generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        // Convert UUID to long
        long id = uuid.getMostSignificantBits() & Long.MAX_VALUE;
        return id;
    }

    public void saveVaccinesInBulk(String manufacturer, Date dateOfManufacture, Date expiryDate, int numberOfVaccines) {
        List<Vaccines> vaccinesToSave = new ArrayList<>();
        for (int i = 0; i < numberOfVaccines; i++) {
            Long generatedId = generateUniqueId(); // Implement this method to generate unique IDs
            Vaccines vaccine = new Vaccines();
            vaccine.setVaccineId(generatedId);
            vaccine.setManufacturer(manufacturer);
            vaccine.setDateOfManufacture(dateOfManufacture);
            vaccine.setExpiryDate(expiryDate);
          
            
            vaccinesToSave.add(vaccine);
        }
       
        vaccinesRepository.saveAll(vaccinesToSave);
    }

    
}