package com.CodeLab.Central_Service.contoller;



import com.CodeLab.Central_Service.model.Problem;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class IntegrationCheckingController {

    @CrossOrigin(origins = "*")

    @GetMapping
    public Problem check() {
        return new Problem();
    }
}
