package com.hughes.vms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hughes.vms.model.Patients;

@Repository
public interface PatientsRepository extends JpaRepository<Patients,Long> {

	Patients findByPhoneNumber(String phoneNumber);

}
