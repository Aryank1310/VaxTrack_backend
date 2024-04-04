package com.hughes.vms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hughes.vms.model.Appointments;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {

	 Appointments findByPatientId( Long patientId);
	 List<Appointments> findByCenterId(int centerId);

	
}