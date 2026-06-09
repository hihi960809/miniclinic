package tw.edu.fju.miniclinic.model;

import jakarta.validation.constraints.Size;

public class PasswordForm {
    private String oldPassword;

    @Size(min = 8, message = "新密碼至少需 8 碼")
    private String newPassword;
    
    private String confirmPassword;

    // 手動補上 Getter 和 Setter
    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}