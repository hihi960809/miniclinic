package tw.edu.fju.miniclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore; 

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @Column(name = "doctor_id", length = 10)
    private String doctorId;      // 醫師編號

    @Column(name = "name", length = 50, nullable = false)
    private String name;          // 姓名

    @Column(name = "department", length = 20, nullable = false)
    private String department;    // 科別

    @Column(name = "specialty", length = 100)
    private String specialty;     // 專長


    @JsonIgnore
    @Column(name = "password_hash", length = 100)
    private String passwordHash;

    // 建構子、getters、setters...
	
	// 新增：密碼 getters and setters
	public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // 1. JPA 需要的無參數建構子（這個一定要留著，不然 JPA 會報錯）
    public Doctor() {}

    // 2. 你原本寫的帶參數建構子（手動 new 物件或測試時很方便）
    public Doctor(String doctorId, String name, String department, String specialty) {
        this.doctorId = doctorId;
        this.name = name;
        this.department = department;
        this.specialty = specialty;
    }

    // 3. Getters（Spring 和 Hibernate 會透過這些方法讀取欄位）
    public String getDoctorId() { return doctorId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getSpecialty() { return specialty; }

    // 4. Setters（更新資料或資料庫回填時會用到）
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}