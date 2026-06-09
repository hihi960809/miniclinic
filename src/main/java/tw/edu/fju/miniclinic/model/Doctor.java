package tw.edu.fju.miniclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore; 
// 💡 新增：引入 Spring Security 需要的套件
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "doctor")
public class Doctor implements UserDetails { // 🌟 修改：讓類別實作 UserDetails

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


    // =========================================================================
    // 🌟 5. 新增：以下為 Spring Security UserDetails 必備的安全驗證邏輯
    // =========================================================================
    
    @Override
    public String getPassword() {
        return this.passwordHash; // 🎯 關鍵：引導框架來這裡讀取密碼雜湊值
    }

    @Override
    public String getUsername() {
        return this.doctorId;     // 🎯 關鍵：引導框架將 doctorId 視為登入帳號
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 暫不設定角色權限，回傳空列表
    }

    @Override
    public boolean isAccountNonExpired() { return true; } // 帳號未過期

    @Override
    public boolean isAccountNonLocked() { return true; }  // 帳號未鎖定

    @Override
    public boolean isCredentialsNonExpired() { return true; } // 憑證未過期

    @Override
    public boolean isEnabled() { return true; } // 帳號可用
}