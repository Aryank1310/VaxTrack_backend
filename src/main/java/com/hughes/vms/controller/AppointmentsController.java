package com.hughes.vms.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hughes.vms.model.Appointments;
import com.hughes.vms.services.AppointmentsService;
import com.hughes.vms.services.VaccinationRecordsService;
import com.hughes.vms.model.Appointments;
import com.hughes.vms.model.Appointments.Status;
import com.hughes.vms.model.Vaccination_records;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class AppointmentsController {
    @Autowired
    AppointmentsService appointmentService;
    
    @Autowired
    VaccinationRecordsService vrecordservice;

    @RequestMapping(value="/appointments", method=RequestMethod.GET)
    public List<Appointments> readAppointments() {
        return appointmentService.getAllAppointments();
    }
    @RequestMapping(value = "/appointments/{centerId}", method = RequestMethod.GET)
    public List<Appointments> readAppointmentsByCenterId(@PathVariable Integer centerId) {
        return appointmentService.getAppointmentByCenterID(centerId);
//                .orElseThrow(() -> new RuntimeException("Appointments not found for center with id: " + centerId));
    }


    @RequestMapping(value = "/appointments/phone/{phoneNumber}", method = RequestMethod.GET)
    public Appointments readPatientByPhoneNo(@PathVariable String phoneNumber) {
        Optional<Appointments> appointment = appointmentService.getByPhoneNumber(phoneNumber);
        return appointment.orElseThrow(() -> new RuntimeException("Appointment not found for phone number: " + phoneNumber));
    }


    @RequestMapping(value = "/appointments/register", method = RequestMethod.POST)
    public Appointments registerAppointment(@RequestBody Appointments app) {
        return appointmentService.scheduleAppointment(app.getPatientId(), app.getCenterId(), app.getAppointmentDate());
    }
    @PutMapping("/appointments/updateStatus/{appointmentId}")
    public Appointments updateAppointmentStatus(@PathVariable int appointmentId, @RequestParam("status") Status status) {
        Appointments updatedAppointment = appointmentService.updateAppointmentStatus(appointmentId, status);
        
        // Check if the status is completed
        if (status == Status.Completed) {
            // Create a new vaccination record
            Vaccination_records vaccinationRecord = new Vaccination_records();
            vaccinationRecord.setPatientId(updatedAppointment.getPatientId());
            vaccinationRecord.setVaccineId(updatedAppointment.getVaccineId());
            vaccinationRecord.setVaccinationDate(new java.sql.Timestamp(System.currentTimeMillis()));
            // Convert centerId to Long before setting it
            vaccinationRecord.setVaccinationCenterId(Long.valueOf(updatedAppointment.getCenterId()));
            
            // Add the vaccination record
            vrecordservice.addVaccinationRecord(vaccinationRecord);
        }

        return updatedAppointment;
    }



}
