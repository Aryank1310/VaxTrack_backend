package com.hughes.vms.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hughes.vms.model.Patients;
import com.hughes.vms.model.Patients.Gender;
import com.hughes.vms.repository.PatientsRepository;

@Service
public class PatientsService {
	@Autowired
	PatientsRepository pRepo;

	public List<Patients> getPatients() {
		return pRepo.findAll();
	}

	public Patients registerPatient(String firstName, String lastName, String address, String phoneNumber, String email,
			Date dob, Gender gender) {
		Patients patient = new Patients();
		patient.setFirstName(firstName);
		patient.setLastName(lastName);
		patient.setAddress(address);
		patient.setPhoneNumber(phoneNumber);
		patient.setEmail(email);
		patient.setDob(dob);
		patient.setGender(gender);

		return pRepo.save(patient);
	}

	public Optional<Patients> readByPatientId(Long patientId) {
		return pRepo.findById(patientId);
	}

	public Patients readByPhoneNumber(String phoneNumber) {
		return pRepo.findByPhoneNumber(phoneNumber);
	}
	
}
