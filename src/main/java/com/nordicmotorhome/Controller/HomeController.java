package com.nordicmotorhome.Controller;

import com.nordicmotorhome.Model.Country;
import com.nordicmotorhome.Model.Customer;
import com.nordicmotorhome.Model.Motorhome;
import com.nordicmotorhome.Model.Zip;
import com.nordicmotorhome.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CustomerService customerService;
    @Autowired
    DatabaseService databaseService;
    @Autowired
    MotorhomeService motorhomeService;
    @Autowired
    CountryService countryService;
    @Autowired
    ZipService zipService;

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
        System.out.println("Customer country: " + customerList.get(0).getCountry());
        model.addAttribute("customerList", customerList);
        return "home/showAllCustomers";
    }

    @GetMapping("/addNewCustomer")
    public String addNewCustomer(Model model){
        List<Country> countries = countryService.fetchAllCountries();
        model.addAttribute("countries",countries);
        return "home/addNewCustomer";
    }

    /**
     * @Author Kasper N. Jensen
     * @param customer Customer
     * @return String/URL
     */
    @PostMapping("/addNewCustomer")
    public String addNewCustomer(@ModelAttribute Customer customer) {
        boolean emptyField =
                customer.getFirst_name().isEmpty() || customer.getLast_name().isEmpty() ||
                        customer.getMobile().isEmpty() || customer.getPhone().isEmpty() || customer.getEmail().isEmpty() ||
                        customer.getDrivers_license().isEmpty() || customer.getDl_issue_date().isEmpty() ||
                        customer.getDl_expire_date().isEmpty() || customer.getStreet().isEmpty() ||
                        customer.getFloor().isEmpty() || customer.getZip().isEmpty() || customer.getCity().isEmpty() ||
                        customer.getCountry().isEmpty();

        if (emptyField) {
            return "home/error/errorPage";
        }

        customerService.addCustomer(customer);
        return "redirect:/showAllCustomers";
    }

    @GetMapping("/updateCustomer/{id}")
    public String updateCustomer(@PathVariable("id") int id, Model model) {
        Customer customer = customerService.findCustomerByID(id);
        List<Country> countries = countryService.fetchAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute(customer);
        return "home/updateCustomer";
    }

    @PostMapping("/updateCustomer/{id}")
    public String updateCustomer(@PathVariable("id") int id, @ModelAttribute Customer customer) {
        customerService.updateCustomer(customer, id);
        return "redirect:/showAllCustomers";
    }


    /**
     * @author Mads
     * @param id the customer to delete
     * @return url to the page that displays all customers
     */
    @GetMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable("id") int id) {
        customerService.deleteCustomer(id);
        return "redirect:/showAllCustomers";
    }

    @GetMapping("/showAllMotorhomes")
    public String showAllMotorhomes(Model model){
        List<Motorhome> motorhomeList = motorhomeService.fetchAllMotorhomes();
        model.addAttribute("motorhomeList", motorhomeList);
        return "home/showAllMotorhomes";
    }

    /**
     * Author Kasper N. Jensen
     * @return String url to the displayed page
     */
    @GetMapping("/addNewMotorhome")
    public String addNewMotorhome(){
        return "home/addNewMotorhome";
    }

    @PostMapping("/addNewMotorhome")
    public String addNewMotorhome(@ModelAttribute Motorhome motorhome){
        motorhomeService.addMotorhome(motorhome);
        return "redirect:/showAllMotorhomes";
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
