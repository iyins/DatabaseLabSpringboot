package com.example.lab4database.controllers;

import com.example.lab4database.models.Patient;
import com.example.lab4database.services.DatabaseOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private DatabaseOperations databaseOperations;

    @GetMapping("")
    public String getAllPatients(Model model) {
        model.addAttribute("patients", databaseOperations.getPatients());

        String message = (String )model.getAttribute("message");
        model.addAttribute("message", message);

        return "patients";
    }

    @GetMapping("/add")
    public String addPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "add_patient";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("patient") Patient model, RedirectAttributes redirectAttributes) {
        databaseOperations.createPatient(model);

        redirectAttributes.addFlashAttribute("message", "New patient added successfully");
        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String editPatient(@PathVariable("id") int id, Model model) {
        Patient patient = databaseOperations.getPatientById(id);
        if (patient != null) {
            model.addAttribute("patient", patient);
            return "edit_patient";
        }
        return "redirect:/patients";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") int id, @ModelAttribute("patient") Patient patient,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "edit_patient";
        }
        patient.setId(id);
        databaseOperations.updatePatient(patient);

        redirectAttributes.addFlashAttribute("message", "Patient updated successfully");

        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        databaseOperations.deletePatient(id);
        redirectAttributes.addFlashAttribute("message", "Patient deleted successfully");
        return "redirect:/patients";
    }
}
