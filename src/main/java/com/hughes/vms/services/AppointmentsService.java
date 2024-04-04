package com.hughes.vms.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hughes.vms.model.Appointments;
import com.hughes.vms.model.Patients;
import com.hughes.vms.repository.AppointmentsRepository;
import com.hughes.vms.repository.PatientsRepository;

@Service
public class AppointmentsService {
    @Autowired
    AppointmentsRepository appointmentRepository;
    @Autowired
	PatientsRepository pRepo;

    
    public List<Appointments> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public Appointments scheduleAppointment(long patientId, int centerId, long vaccineId, Date appointmentDate) {
        Appointments appointment = new Appointments();
        appointment.setPatientId(patientId);
        appointment.setCenterId(centerId);
        appointment.setVaccineId(vaccineId);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setStatus(Appointments.Status.Scheduled);
        
        return appointmentRepository.save(appointment);
    }



    public Optional<Appointments> getAppointmentsById(int appoinmtmentid) {
        return appointmentRepository.findById(appoinmtmentid);
    }
    public Optional<Appointments> getByPhoneNumber(String phoneNumber) {
        Patients patient = pRepo.findByPhoneNumber(phoneNumber);
        if (patient != null) {
            Appointments appointment = appointmentRepository.findByPatientId(patient.getPatientId());
            return Optional.ofNullable(appointment);
        }
        return Optional.empty(); 
    }

    		
    		
    
    public List<Appointments> getAppointmentByCenterID(int centerID){
    	return appointmentRepository.findByCenterId(centerID);
    }

    

    // You can add more methods here for handling other operations like creating, updating, and deleting appointments
}
