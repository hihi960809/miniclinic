package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody; // 確保前端能正確收到統計資料

import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.AppointmentRepository; // 🌟 必須加上這一行

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;


@RestController
public class DoctorApiController {

    @PutMapping("/api/doctors/{doctorId}")
public ResponseEntity<Doctor> updateDoctor(
        @PathVariable String doctorId,
        @RequestBody Doctor updated) {

    return doctorRepo.findById(doctorId)
        .map(existing -> {
            existing.setName(updated.getName());
            existing.setDepartment(updated.getDepartment());
            existing.setSpecialty(updated.getSpecialty());
            return ResponseEntity.ok(doctorRepo.save(existing));
        })
        .orElse(ResponseEntity.notFound().build());
}
@DeleteMapping("/api/doctors/{doctorId}")
public ResponseEntity<Void> deleteDoctor(@PathVariable String doctorId) {
    if (!doctorRepo.existsById(doctorId)) {
        return ResponseEntity.notFound().build();
    }
    doctorRepo.deleteById(doctorId);
    return ResponseEntity.noContent().build();  // 204 No Content
}

@PostMapping("/api/doctors")
public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor newDoctor) {
    // 檢查這名醫師 ID 是否已經存在，避免覆蓋掉別人的資料
    if (doctorRepo.existsById(newDoctor.getDoctorId())) {
        return ResponseEntity.badRequest().build(); // 回傳 400 錯誤
    }
    // 儲存進資料庫
    Doctor saved = doctorRepo.save(newDoctor);
    return ResponseEntity.ok(saved); // 回傳 200 成功與存好的資料
}



    @Autowired
    private AppointmentRepository appointmentRepo;



    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping("/api/doctors")
    public List<Doctor> getDoctors(
            @RequestParam(required = false) String department) {
        if (department == null || department.isBlank()) {
            return doctorRepo.findAll();
        }
        return doctorRepo.findByDepartment(department);
    }

    @GetMapping("/api/doctors/{doctorId}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable String doctorId) {
        Optional<Doctor> doctor = doctorRepo.findById(doctorId);
        return doctor
            .map(d -> ResponseEntity.ok(d))       // 有 → 200 OK + 資料
            .orElse(ResponseEntity.notFound().build());  // 沒有 → 404
    }

    @GetMapping("/api/departments")
    public List<String> getDepartments() {
        return doctorRepo.findAllDepartments();
    }
    // 🌟 在最上方確保有 import java.util.Map 和 java.util.HashMap
    @GetMapping("/api/appointments/stats")
    public ResponseEntity<Map<String, Object>> getAppointmentStats() {
        Map<String, Object> stats = new java.util.HashMap<>();
        
        // 1. 計算各科別掛號人次統計
        List<Object[]> deptStats = appointmentRepo.countAppointmentsByDepartment();
        stats.put("departments", deptStats);
        
        // 2. 透過 doctorRepo 計算全院醫師總數 (JPA 內建的 count() 功能)
        long totalDoctors = doctorRepo.count();
        stats.put("totalDoctors", totalDoctors);
        
        // 3. 如果你的專案有 patientRepo，就用它算總數；
        // 如果沒有宣告，我們可以直接用 appointmentRepo 去計算目前有多少「不重複」的病歷號
        // 這行 SQL 會自動幫你算出不重複的病患總人數
        long totalPatients = appointmentRepo.countDistinctPatient(); 
        stats.put("totalPatients", totalPatients);
        
        return ResponseEntity.ok(stats);
    }
}