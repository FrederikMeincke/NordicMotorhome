package com.nordicmotorhome.Controller;

import com.nordicmotorhome.Model.Customer;
import com.nordicmotorhome.Service.CustomerService;
import com.nordicmotorhome.Service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CustomerService customerService;
    @Autowired
    DatabaseService databaseService;

    @GetMapping("/")
    public String index() {
        databaseService.initializeDatabase();
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

    @GetMapping("/addNewCustomer")
    public String addNewCustomer(){
        return "home/addNewCustomer";
    }

    @PostMapping("/addNewCustomer")
    public String addNewCustomer(@ModelAttribute Customer customer) {
        boolean emptyField =
                        customer.getFirst_name().isEmpty() || customer.getLast_name().isEmpty() ||
                        customer.getMobile().isEmpty() || customer.getPhone().isEmpty() || customer.getEmail().isEmpty() ||
                        customer.getDrivers_license().isEmpty() || customer.getDl_issue_date().isEmpty() ||
                        customer.getDl_expire_date().isEmpty() || customer.getStreet().isEmpty() ||
                        customer.getFloor().isEmpty() || customer.getZip() == 0 || customer.getCity().isEmpty() ||
                        customer.getCountry().isEmpty();

        if (emptyField) {
            return "home/error/errorPage";
        }
        customerService.addCustomer(customer);
        return "redirect:/showAllCustomers";
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
