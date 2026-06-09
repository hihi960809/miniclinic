package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.fju.miniclinic.model.Patient;
import tw.edu.fju.miniclinic.model.PatientRepository;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepo;

    // 1. 網頁頁面：GET /patients
    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientRepo.findAll());
        return "patients"; // 對應 templates/patients.html
    }

    // 2. REST API：GET /api/patients
    @GetMapping("/api/patients")
    @ResponseBody // 讓 @Controller 也能回傳 JSON 資料
    public List<Patient> getPatientsApi() {
        return patientRepo.findAll();
    }
}