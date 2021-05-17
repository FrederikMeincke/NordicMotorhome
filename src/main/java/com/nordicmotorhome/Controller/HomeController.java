package com.nordicmotorhome.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/showAllCustomers")
    public String showAllCustomers(){
        return "home/showAllCustomers";
    }

    @GetMapping("/showAllMotorhomes")
    public String showAllMotorhomes(){
        return "home/showAllMotorhomes";
    }

    @GetMapping("/showAllRentals")
    public String showAllRentals(){
        return "home/showAllRentals";
    }

    @GetMapping("/showAllServiceReports")
    public String showAllServiceReports(){
        return "home/showAllServiceReports";
    }
}
