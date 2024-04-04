package com.hughes.vms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hughes.vms.model.Vaccination_records;
@Repository
public interface VaccinationRecordsRepository extends JpaRepository<Vaccination_records, Long> {
	Vaccination_records findByPatientId(Long patientId);
}
