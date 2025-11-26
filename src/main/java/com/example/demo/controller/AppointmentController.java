package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    @GetMapping("/doctor/{doctorName}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable String doctorName) {
        return appointmentService.getAppointmentsByDoctor(doctorName);
    }

    @GetMapping("/patient/{patientName}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable String patientName) {
        return appointmentService.getAppointmentsByPatient(patientName);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}
