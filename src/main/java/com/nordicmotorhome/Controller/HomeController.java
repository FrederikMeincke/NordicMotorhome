package com.nordicmotorhome.Controller;

import com.nordicmotorhome.Model.*;
import com.nordicmotorhome.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
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
    AccessoryService accessoryService;
    @Autowired
    RentalService rentalService;

    @GetMapping("/")
    public String index() {
        databaseService.initializeDatabase();
        return "home/index";
    }

    // CUSTOMER
    @GetMapping("/showAllCustomers")
    public String showAllCustomers(Model model){
        List<Customer> customerList = customerService.fetchAll();
        model.addAttribute("customerList", customerList);
        return "home/showAllCustomers";
    }

    @GetMapping("/showAllCustomers/{sort}")
    public String showAllCustomers(@PathVariable("sort") String sort, Model model) {
        List<Customer> customerList = customerService.fetchAll();
        customerService.sort(customerList, sort);
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
     * @author Kasper N. Jensen
     * @param customer Customer
     * @return String/URL
     */
    @PostMapping("/addNewCustomer")
    public String addNewCustomer(@ModelAttribute Customer customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping("/updateCustomer/{id}")
    public String updateCustomer(@PathVariable("id") int id, Model model) {
        Customer customer = customerService.findCustomerByID(id);
        List<Country> countries = countryService.fetchAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("customer", customer);
        return "home/updateCustomer";
    }

    @PostMapping("/updateCustomer/{id}")
    public String updateCustomer(@PathVariable("id") int id, @ModelAttribute Customer customer) {
        return customerService.updateCustomer(customer, id);
    }

    /**
     * @author Mads
     * @param id the customer to delete
     * @return url to the page that displays all customers
     */
    @GetMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable("id") int id) {
        return customerService.deleteCustomer(id);
    }

    // MOTORHOMES
    @GetMapping("/showAllMotorhomes")
    public String showAllMotorhomes(Model model){
        List<Motorhome> motorhomeList = motorhomeService.fetchAllMotorhomes();
        model.addAttribute("motorhomeList", motorhomeList);
        return "home/showAllMotorhomes";
    }

    @GetMapping("/showMotorhome/{id}")
    public String showMotorhome(@PathVariable("id") int id, Model model) {
        Motorhome motorhome = motorhomeService.findMotorhomeById(id);
        List<Motorhome> motorhomeList= new ArrayList<>();
        motorhomeList.add(motorhome);
        model.addAttribute("motorhomeList", motorhomeList);
        return "home/showAllMotorhomes";
    }

    /**
     * @author Kasper N. Jensen
     * @return String url to the displayed page
     */
    @GetMapping("/addNewMotorhome")
    public String addNewMotorhome(){
        return "home/addNewMotorhome";
    }

    @PostMapping("/addNewMotorhome")
    public String addNewMotorhome(@ModelAttribute Motorhome motorhome){
        return motorhomeService.addMotorhome(motorhome);
    }

    @GetMapping("/deleteMotorhome/{id}")
    public String deleteMotorhome(@PathVariable("id") int id) {
        return motorhomeService.deleteMotorhome(id);
    }

    @GetMapping("/updateMotorhome/{id}")
    public String updateMotorhome(@PathVariable("id") int id, Model model) {
        Motorhome motorhome = motorhomeService.findMotorhomeById(id);
        model.addAttribute("motorhome", motorhome);
        return "home/updateMotorhome";
    }

    @PostMapping("/updateMotorhome/{id}")
    public String updateMotorhome(@PathVariable("id") int id, @ModelAttribute Motorhome motorhome) {
        motorhomeService.updateMotorhome(motorhome, id);
        return "redirect:/showAllMotorhomes";
    }

    // RENTALS
    @GetMapping("/showAllRentals")
    public String showAllRentals(Model model){
        List<Rental> rentalList = rentalService.fetchAll();
        model.addAttribute("rentalList", rentalList);
        return "home/showAllRentals";
    }

    @GetMapping("/addNewRental")
    public String addNewRental(Model model) {
        List<Accessory> accessoryList = accessoryService.fetchAll();
        model.addAttribute("accessories", accessoryList);
        List<Motorhome> motorhomeList = motorhomeService.fetchAllMotorhomes();
        model.addAttribute("motorhomes", motorhomeList);
        return "home/addNewRental";
    }

    @PostMapping("/addNewRental")
    public String addNewRental(@ModelAttribute Rental rental) {
        rentalService.addNew(rental);
        return "redirect:/showAllRentals";
    }

    @GetMapping("/deleteRental/{id}")
    public String deleteRental(@PathVariable("id") int id) {
        return rentalService.delete(id);
    }

    @GetMapping("/updateRental/{id}")
    public String updateRental(@PathVariable("id") int id, Model model) {
        Rental rental = rentalService.findById(id);
        model.addAttribute("rental", rental);
        List<Accessory> accessoryList = accessoryService.fetchAll();
        model.addAttribute("accessories", accessoryList);
        List<Motorhome> motorhomeList = motorhomeService.fetchAllMotorhomes();
        model.addAttribute("motorhomes", motorhomeList);
        return "home/updateRental";
    }

    @PostMapping("/updateRental/{id}")
    public String updateRental(@PathVariable("id") int id, @ModelAttribute Rental rental) {
        rentalService.update(rental, id);
        return "redirect:/showAllRentals";
    }

    // ACCESSORIES
    @GetMapping("/showAllAccessories")
    public String showAllAccessories(Model model) {
        List<Accessory> accessoryList = accessoryService.fetchAll();
        model.addAttribute("accessoryList", accessoryList);
        return "home/showAllAccessories";
    }

    @GetMapping("/showAllAccessories/{sort}")
    public String showAllAccessories(@PathVariable("sort") String sort, Model model) {
        List<Accessory> accessoryList = accessoryService.fetchAll();
        accessoryService.sort(accessoryList, sort);
        model.addAttribute("accessoryList", accessoryList);
        return "home/showAllAccessories";
    }

    @GetMapping("/updateAccessory/{id}")
    public String updateAccessory(@PathVariable("id") int id, Model model) {
        Accessory accessory = accessoryService.findById(id);
        model.addAttribute("accessory", accessory);
        return "home/updateAccessory";
    }

    @PostMapping("/updateAccessory/{id}")
    public String updateAccessory(@PathVariable("id") int id, @ModelAttribute Accessory accessory) {
        accessoryService.update(id, accessory);
        return "redirect:/showAllAccessories";
    }
}