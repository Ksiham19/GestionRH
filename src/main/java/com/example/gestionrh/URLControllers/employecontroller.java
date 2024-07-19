package com.example.gestionrh.URLControllers;

import com.example.gestionrh.Entities.Employe;
import com.example.gestionrh.Entities.ResponsableRH;
import com.example.gestionrh.Services.EmployeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/employe")
public class employecontroller {
    EmployeService employeService;

    public employecontroller(EmployeService employeService){
        this.employeService=employeService;
    }

    @GetMapping("/deconnecter")
    public String deconnecter() {
        return "index";
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, Object> request) {
        Long employeId = Long.parseLong(request.get("employeId").toString());
        String currentPassword = request.get("currentPassword").toString();
        String newPassword = request.get("newPassword").toString();

        Map<String, Object> response = new HashMap<>();
        boolean success = employeService.changePassword(employeId, currentPassword, newPassword);

        if (success) {
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Échec du changement de mot de passe. Veuillez réessayer.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
