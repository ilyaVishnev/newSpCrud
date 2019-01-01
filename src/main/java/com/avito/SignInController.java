package com.avito;

import com.DAO.services.HolderService;
import com.cars_annot.Holder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sign")
public class SignInController {

    @Autowired
    private HolderService holderService;

    @GetMapping
    protected String goToAccount() {
        return "signIn";
    }

    @PostMapping
    protected String toList(HttpServletRequest req) {
        Holder holder = holderService.findByLoginAndPassword(req.getParameter("login"), req.getParameter("password"));
        if (holder == null) {
            req.setAttribute("error", "there isnt such user");
            return "signIn";
        } else {
            req.getSession().setAttribute("user", holder);
            return "list";
        }
    }
}
