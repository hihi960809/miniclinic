package tw.edu.fju.miniclinic.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import org.springframework.stereotype.Repository;
import java.util.List;
import tw.edu.fju.miniclinic.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT d.department, COUNT(a) FROM Appointment a JOIN a.doctor d GROUP BY d.department")
    List<Object[]> countAppointmentsByDepartment();

    @Query("SELECT COUNT(DISTINCT a.patient) FROM Appointment a")
    long countDistinctPatient();
    // 新增這個方法，Spring Data JPA 會自動幫你寫好 SELECT COUNT(...) WHERE status = ?
    long countByStatus(String status);

    List<Appointment> findByApptDate(LocalDate apptDate);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByDoctorAndApptDate(Doctor doctor, LocalDate apptDate);
    long countByApptDateBetween(LocalDate from, LocalDate to);
    // 🌟 請在檔案裡加上這一行新方法
    List<Appointment> findByDoctorAndStatus(Doctor doctor, String status);
}
