package com.avito;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/deniedPage")
public class DeniedController {
    @PostMapping
    public String beingDenied() {
        return "deniedPage";
    }
}
