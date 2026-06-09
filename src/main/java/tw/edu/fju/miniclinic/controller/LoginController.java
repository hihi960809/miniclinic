package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import tw.edu.fju.miniclinic.model.*;

@Controller
public class LoginController {

    @Autowired
    private DoctorRepository doctorRepo;

    // --- 原有的登入相關功能 (未更動) ---
    @GetMapping("/login")
    public String loginForm(Model model) {
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm());
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) return "login";

        // 🔒 請把這段改回來
Doctor doctor = doctorRepo.findById(form.getDoctorId()).orElse(null);
if (doctor == null || !BCrypt.checkpw(form.getPassword(), doctor.getPasswordHash())) { // 恢復這行
    model.addAttribute("errorMessage", "醫師編號或密碼錯誤");
    return "login";
}

        session.setAttribute("loggedInDoctorId", doctor.getDoctorId());
        session.setAttribute("loggedInDoctorName", doctor.getName());
        return "redirect:/dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // --- 🌟 新增：修改密碼功能 ---

    @GetMapping("/password")
    public String passwordForm(Model model) {
        model.addAttribute("pwdForm", new PasswordForm());
        return "password";
    }

    @PostMapping("/password")
    public String updatePassword(@Valid @ModelAttribute("pwdForm") PasswordForm form,
                                 BindingResult result, HttpSession session, Model model) {
        
        // 1. 格式驗證失敗
        if (result.hasErrors()) return "password";

        // 2. 檢查新密碼與確認密碼是否一致
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("error", "新密碼與確認密碼不一致");
            return "password";
        }

        // 3. 取得目前登入的醫師
        String doctorId = (String) session.getAttribute("loggedInDoctorId");
        if (doctorId == null) return "redirect:/login"; // 未登入則導向登入頁

        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow();

        // 4. 驗證舊密碼是否正確
        if (!BCrypt.checkpw(form.getOldPassword(), doctor.getPasswordHash())) {
            model.addAttribute("error", "舊密碼錯誤");
            return "password";
        }

        // 5. 更新密碼 (使用 BCrypt 加密新密碼)
        String hashedNewPassword = BCrypt.hashpw(form.getNewPassword(), BCrypt.gensalt());
        doctor.setPasswordHash(hashedNewPassword);
        doctorRepo.save(doctor);

        model.addAttribute("message", "密碼修改成功！");
        return "password"; // 或導向其他頁面
    }
}