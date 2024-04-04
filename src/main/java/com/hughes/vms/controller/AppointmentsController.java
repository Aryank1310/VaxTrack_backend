package com.hughes.vms.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hughes.vms.model.Appointments;
import com.hughes.vms.services.AppointmentsService;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class AppointmentsController {
    @Autowired
    AppointmentsService appointmentService;

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
        return appointmentService.scheduleAppointment(app.getPatientId(), app.getCenterId(), app.getVaccineId(), app.getAppointmentDate());
    }
}
