package com.nordicmotorhome.Controller;

import com.nordicmotorhome.Model.Customer;
import com.nordicmotorhome.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public String index() {
        return "home/index";
    }

    @GetMapping("/loginPage")
    public String loginPage(){
        return "home/loginPage";
    }

    @GetMapping("/showAllCustomers")
    public String showAllCustomers(Model model){
        List<Customer> customerList = customerService.fetchAllCustomers();
        model.addAttribute("customerList", customerList);
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
