package com.hughes.vms.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hughes.vms.model.Appointments;
import com.hughes.vms.model.Patients;
import com.hughes.vms.model.Vaccines;
import com.hughes.vms.repository.AppointmentsRepository;
import com.hughes.vms.repository.PatientsRepository;
import com.hughes.vms.repository.VaccinesRepository;

@Service
public class AppointmentsService {
    @Autowired
    AppointmentsRepository appointmentRepository;
    @Autowired
	PatientsRepository pRepo;
    @Autowired
    private VaccinesRepository vaccineRepository;

    
    public List<Appointments> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public Appointments scheduleAppointment(long patientId, int centerId, Date appointmentDate) {
    	Vaccines unassignedVaccine = vaccineRepository.findFirstByAssignedFalse();
    	
    	 if (unassignedVaccine != null) {
             // Create a new appointment
             Appointments appointment = new Appointments();
             appointment.setPatientId(patientId);
             appointment.setCenterId(centerId);
             appointment.setAppointmentDate(appointmentDate);
             appointment.setVaccineId(unassignedVaccine.getVaccineId());
             appointment.setStatus(Appointments.Status.Scheduled);

             // Save the appointment
             appointmentRepository.save(appointment);

             // Update the vaccine's assigned status
             unassignedVaccine.setAssigned(true);
             vaccineRepository.save(unassignedVaccine);

             // Return the appointment details
             return appointment;
         } else {
             // Log a message indicating that vaccines are out of stock
             System.out.println("Vaccine is out of Stock");
             
             // Alternatively, you can throw an exception to indicate the issue.
             // throw new RuntimeException("No unassigned vaccines are available. Vaccines are out of stock.");
             return null; // or handle the case when no vaccine is available
         }
     
    	
        
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
