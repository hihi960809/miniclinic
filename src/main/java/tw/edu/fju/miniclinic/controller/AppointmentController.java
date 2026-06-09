package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// 🌟 新增這兩行 import 以支援 API 功能
import java.util.Map;
import java.util.HashMap;

import tw.edu.fju.miniclinic.model.*;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepo;
    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/appointment/new")
    public String newAppointmentForm(Model model) {
        model.addAttribute("form", new AppointmentForm());
        model.addAttribute("doctors", doctorRepo.findAll());
        return "appointment-new";
    }

    @PostMapping("/appointment/new")
    public String submitAppointment(
            @Valid @ModelAttribute("form") AppointmentForm form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("form", form);
            model.addAttribute("doctors", doctorRepo.findAll());
            return "appointment-new";
        }

        AppointmentForm dto = form;
        Patient patient = patientRepo.findById(form.getChartNo()).orElse(null);
        Doctor doctor = doctorRepo.findById(form.getDoctorId()).orElse(null);

        Appointment appt = new Appointment();
        appt.setPatient(patient);
        appt.setDoctor(doctor);
        appt.setApptDate(java.time.LocalDate.parse(dto.getApptDate()));
        appt.setTimeSlot(dto.getTimeSlot());
        appt.setStatus("BOOKED");

        Appointment saved = appointmentRepo.save(appt);
        model.addAttribute("appointment", saved);

        return "appointment-result";
    }

    // 🌟 這是你要求的 API 功能，放在這裡即可，完全沒動到上面的邏輯
    // 🌟 修改為 ResponseEntity 回傳格式，確保前端 Fetch 能正確接收到 ok (200) 狀態碼
}